package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.util.HerdManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Objects;
import java.util.UUID;

public class HerdGoal extends Goal {

    private static final int MEMBERSHIP_RECHECK_INTERVAL = 500;
    private static final int PATH_RECALC_INTERVAL = 20;

    // Debug: tints the herd id nametag. Both instances claim Flag.MOVE so only one runs at a
    // time, which is why each can own the colour without coordinating with the other.
    private static final ChatFormatting WANDERING_COLOUR = ChatFormatting.GREEN;
    private static final ChatFormatting LEASH_FOLLOW_COLOUR = ChatFormatting.AQUA;
    private static final ChatFormatting STOPPED_COLOUR = ChatFormatting.WHITE;

    // Why a running goal produced no movement.
    private static final ChatFormatting NO_STEERING_COLOUR = ChatFormatting.LIGHT_PURPLE;
    private static final ChatFormatting PREEMPTED_COLOUR = ChatFormatting.GOLD;

    private final EnhancedAnimalAbstract entity;
    private final PathNavigation navigation;
    private final double speed;
    private final boolean leashFollow;

    private int membershipTimer;
    private int pathTimer = 0;
    private ChatFormatting lastDiagnosis = STOPPED_COLOUR;
    private EnhancedAnimalAbstract leashedLeader;

    public HerdGoal(EnhancedAnimalAbstract entity, double speed) {
        this(entity, speed, false);
    }

    public HerdGoal(EnhancedAnimalAbstract entity, double speed, boolean leashFollow) {
        this.entity = entity;
        this.navigation = entity.getNavigation();
        this.speed = speed;
        this.leashFollow = leashFollow;
        // Staggered by id so a batch spawning together doesn't all check on the same tick.
        this.membershipTimer = Math.floorMod(entity.getId(), MEMBERSHIP_RECHECK_INTERVAL);
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (entity.level.isClientSide) return false;
        if (entity.isAnimalSleeping()) return false;

        if (entity.isLeashed()) return false;

        UUID herdId = entity.getHerdId();
        if (herdId == null) return false;

        if (leashFollow) {
            if (!GeneticAnimalsConfig.COMMON.herdLeashFollowEnabled.get()) return false;
            leashedLeader = HerdManager.findLeashedHerdMate(entity);
            return leashedLeader != null;
        }

        // Bookkeeping, not movement: runs whether or not this goal does, and regardless of
        // whether group movement is switched on.
        HerdManager.ensureRegistered(entity);
        maybeRecheckMembership();

        if (!GeneticAnimalsConfig.COMMON.herdMovementEnabled.get()) return false;
        return HerdManager.isHerdMoving(herdId, entity.level.getGameTime());
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public boolean isInterruptable() {
        return true;
    }

    @Override
    public void start() {
        pathTimer = 0;
        lastDiagnosis = leashFollow ? LEASH_FOLLOW_COLOUR : WANDERING_COLOUR;
        setDebugNameColour(lastDiagnosis);
    }

    @Override
    public void stop() {
        navigation.stop();
        leashedLeader = null;
        setDebugNameColour(STOPPED_COLOUR);
    }

    @Override
    public void tick() {
        if (leashFollow) {
            tickLeashFollow();
        } else {
            tickWander();
        }
    }

    private void tickWander() {
        if (--pathTimer <= 0) {
            pathTimer = PATH_RECALC_INTERVAL;
            lastDiagnosis = attemptWanderMove();
        }
        reportDiagnosis();
    }

    private void tickLeashFollow() {
        if (--pathTimer <= 0) {
            pathTimer = PATH_RECALC_INTERVAL;
            lastDiagnosis = attemptLeashFollowMove();
        }
        reportDiagnosis();
    }

    private ChatFormatting attemptWanderMove() {
        Vec3 steering = HerdManager.computeHerdSteering(entity);
        if (steering == null) return NO_STEERING_COLOUR;

        Vec3 target = entity.position().add(steering.scale(8.0));
        navigation.moveTo(target.x, target.y, target.z, speed * HerdManager.catchUpSpeedMultiplier(entity));
        return WANDERING_COLOUR;
    }

    private ChatFormatting attemptLeashFollowMove() {
        if (leashedLeader == null) return NO_STEERING_COLOUR;

        Vec3 steering = HerdManager.computeLeaderSteering(entity, leashedLeader);
        Vec3 target = steering != null
                ? entity.position().add(steering.scale(8.0))
                : leashedLeader.position();
        navigation.moveTo(target.x, target.y, target.z, speed);
        return LEASH_FOLLOW_COLOUR;
    }

    // Grazing declares no flags, so it can hold the navigator alongside us; it wins the readout.
    private void reportDiagnosis() {
        setDebugNameColour(entity.getAIStatus() == AIStatus.EATING ? PREEMPTED_COLOUR : lastDiagnosis);
    }

    // Re-asserted every tick because setHerdId rebuilds the name unstyled mid-burst. Bails when
    // the colour already matches, so it only syncs a name on an actual change.
    private void setDebugNameColour(ChatFormatting colour) {
        Component name = entity.getCustomName();
        if (name == null) return;

        TextColor wanted = TextColor.fromLegacyFormat(colour);
        if (Objects.equals(name.getStyle().getColor(), wanted)) return;

        entity.setCustomName(name.copy().setStyle(Style.EMPTY.withColor(colour)));
    }

    private void maybeRecheckMembership() {
        if (--membershipTimer > 0) return;
        membershipTimer = MEMBERSHIP_RECHECK_INTERVAL;
        if (!HerdManager.checkLeaveHerd(entity)) {
            HerdManager.checkJoinNearbyHerd(entity);
        }
    }

}
