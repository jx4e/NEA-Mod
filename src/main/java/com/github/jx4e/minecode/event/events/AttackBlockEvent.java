package com.github.jx4e.minecode.event.events;

import com.github.jx4e.minecode.event.CancellableEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/**
 * @author Jake (github.com/jx4e)
 * @since 08/06/2022
 **/

public class AttackBlockEvent extends CancellableEvent {
    private PlayerEntity player;
    private World world;
    private Hand hand;
    private BlockPos pos;
    private Direction direction;
    private ActionResult returnVal;

    public AttackBlockEvent(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {
        this.player = player;
        this.world = world;
        this.hand = hand;
        this.pos = pos;
        this.direction = direction;
        returnVal = ActionResult.PASS;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public AttackBlockEvent setPlayer(PlayerEntity player) {
        this.player = player;
        return this;
    }

    public World getWorld() {
        return world;
    }

    public AttackBlockEvent setWorld(World world) {
        this.world = world;
        return this;
    }

    public Hand getHand() {
        return hand;
    }

    public AttackBlockEvent setHand(Hand hand) {
        this.hand = hand;
        return this;
    }

    public BlockPos getPos() {
        return pos;
    }

    public AttackBlockEvent setPos(BlockPos pos) {
        this.pos = pos;
        return this;
    }

    public Direction getDirection() {
        return direction;
    }

    public AttackBlockEvent setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public ActionResult getReturnVal() {
        return returnVal;
    }

    public AttackBlockEvent setReturnVal(ActionResult returnVal) {
        this.returnVal = returnVal;
        return this;
    }
}
