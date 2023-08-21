package tools.redstone.redstonetools.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.RaycastContext;

public class RaycastUtils {
    private RaycastUtils() {
    }

    public static BlockHitResult getBlockHitNeighbor(BlockHitResult hit) {
        var sideOffset = hit.getSide().getUnitVector();

        var newBlockPos = hit.getBlockPos().add(sideOffset.getX(), sideOffset.getY(), sideOffset.getZ());

        return hit.withBlockPos(newBlockPos);
    }

    public static BlockHitResult rayCastFromEye(PlayerEntity player, float reach) {
        return player.getWorld().raycast(new RaycastContext(
                player.getEyePos(),
                player.getEyePos().add(player.getRotationVector().multiply(reach)),
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                player
        ));
    }
}
