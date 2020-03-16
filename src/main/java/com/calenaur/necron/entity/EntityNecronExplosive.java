package com.calenaur.necron.entity;

import com.calenaur.necron.entity.type.EntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class EntityNecronExplosive extends TNTEntity {
    public EntityNecronExplosive(EntityType<? extends TNTEntity> entityType, World world) {
        super(entityType, world);
    }

    private LivingEntity tntPlacedBy;

    public EntityNecronExplosive(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
        this(EntityTypes.NECRON_EXPLOSIVE, worldIn);
        this.setPosition(x, y, z);
        double d0 = worldIn.rand.nextDouble() * (double)((float)Math.PI * 2F);
        this.setMotion(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.tntPlacedBy = igniter;
    }

    @Override
    protected void explode() {
        float f = 4.0F;
        Random random = new Random();

        BlockPos pos = getPosition();
        for (int i = 5; i > 0; i--) {
            //this.world.createExplosion(this, this.func_226277_ct_(), this.func_226283_e_(0.0625D), this.func_226281_cx_(), 4.0F, Explosion.Mode.BREAK);
            this.world.createExplosion(this, pos.getX(), pos.getY(), pos.getZ(), 40.0F,true, Explosion.Mode.BREAK);

            //new Explosion(getEntityWorld(), pos.getX(), pos.getY(), pos.getY());

            Explosion test = new Explosion(getEntityWorld(), null, pos.getX() + random.nextInt(i), pos.getY() + random.nextInt(i), pos.getY() + random.nextInt(i), 20, true, Explosion.Mode.BREAK);

            test.doExplosionA();
            test.doExplosionB(true);
        }
    }

}
