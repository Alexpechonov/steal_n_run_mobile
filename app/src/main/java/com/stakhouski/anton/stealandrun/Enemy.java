package com.stakhouski.anton.stealandrun;

/**
 * Created by archer on 12.11.16.
 */

public class Enemy extends Creature {
    Enemy(int xVal, int yVal)
    {
        setX(xVal);
        setY(yVal);
        setOldBlockType(Field.Type.EMPTY);
        setOldX(getX());
        setOldY(getY());
        setUpdateFlag(true);
    }

    boolean playerCatched(Field field)
    {
        setTestBlockType(field.getBlock(getTestX(), getTestY()));
        return  (getTestBlockType() == Field.Type.PLAYER);
    }

    boolean tick(Field field, Player player) {
        if (!getUpdateFlag())
            return true;

        if (fallTest(field, Field.Type.ENEMY))
            return true;

        setTestX(getX());
        setTestY(getY());

        //go to player quickly
        if (Math.abs(player.getX() - getX()) <
                Math.abs(player.getY() - getY())) {
            if (player.getX() - getX() >= 0){
                setTestX(getTestX() + 1);
            }
            else {
                setTestX(getTestX() - 1);
            }
            if (playerCatched(field)) {
                return false;
            }

            //check testMove at first
            if (!testMovement(field, Field.Type.ENEMY)) {
                setTestX(getX());
                if (player.getY() - getY() >= 0) {
                    setTestY(getTestY() + 1);
                    if (jumpTest(field))
                        return true;
                } else {
                    setTestY(getTestY() - 1);
                }

                if (playerCatched(field)) {
                    return false;
                }

                testMovement(field, Field.Type.ENEMY);
            }
            else{
                return true;
            }
        } else {
            if (!(player.getY() - getY() >= 0)) {
                setTestY(getTestY() - 1);
            } else {
                setTestY(getTestY() + 1);
                if (jumpTest(field)){
                    return !ySecondTest(field, player);
                }
            }

            if (playerCatched(field))
                return false;

            //check testMove at first
            if (!testMovement(field, Field.Type.ENEMY)) {
                if (ySecondTest(field, player)) return false;

            }
            return true;
        }
        return true;
    }

    private boolean ySecondTest(Field field, Player player) {
        setTestY(getY());
        if (player.getX() - getX() >= 0)
            setTestX(getTestX() + 1);
        else
            setTestX(getTestX() - 1);

        if (playerCatched(field))
            return true;

        testMovement(field, Field.Type.ENEMY);
        return false;
    }
}
