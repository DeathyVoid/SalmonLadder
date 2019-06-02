package com.spill.salmonladder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class BobberSprite extends Sprite {
    //
    private Array<EventFisher> arr;
    private EventFisher current;
    private int index = 0, fishermanX, fishermanY;
    private boolean direction = false, inAnimation = false;
    private Array<Timer.Task> movement = new Array<Timer.Task>(4);

    public BobberSprite(Array<EventFisher> arr, Fisherman fisherman) {
        super(new Texture("Sprites/Bobber.png"));
        this.arr = arr;
        this.current = arr.get(index);
        this.fishermanX = fisherman.getX();
        this.fishermanY = fisherman.getY();

        movement.add(new Timer.Task() {
            @Override
            public void run() {
                translate(0, 1f);
            }
        });

        movement.add(new Timer.Task() {
            @Override
            public void run() {
                translate(1f, 0);
            }
        });

        movement.add(new Timer.Task() {
            @Override
            public void run() {
                translate(0, -1f);
            }
        });

        movement.add(new Timer.Task() {
            @Override
            public void run() {
                translate(-1f, 0);
            }
        });

    }

    @Override
    public void draw(Batch batch) {

        if (!inAnimation) {
            if ((getX() - 13) % 32 == 0 && (getY() - 11) % 32 == 0 && noTaskScheduled()) {
                movement();
            }
        } else {
            if (noTaskScheduled()) {
                LevelParser.inAnimation = false;
                LevelParser.inDeath = true;
            }
        }
        super.draw(batch);
    }

    private void movement() {
        EventFisher next;
        current = arr.get(index);
        if (direction) {
            next = arr.get(--index);
            if (index == 0) {
                direction = false;
            }
        } else {
            next = arr.get(++index);
            if (index == arr.size - 1) {
                direction = true;
            }
        }

        if (next.getY() > current.getY()) {
            Timer.schedule(movement.get(0), 0, 1 / 32f, 31);
        } else if (next.getX() > current.getX()) {
            Timer.schedule(movement.get(1), 0, 1 / 32f, 31);
        } else if (next.getY() < current.getY()) {
            Timer.schedule(movement.get(2), 0, 1 / 32f, 31);
        } else if (next.getX() < current.getX()) {
            Timer.schedule(movement.get(3), 0, 1 / 32f, 31);
        }
    }

    public int getEventX(int x) {
        return arr.get(x).getX();
    }

    public int getEventY(int y) {
        return arr.get(y).getY();
    }

    public int getFishermanX() {
        return fishermanX;
    }

    public int getFishermanY() {
        return fishermanY;
    }

    public void animate() {
        inAnimation = true;

        for (Timer.Task i : movement) {
            i.cancel();
        }

        if (getX() / SalmonLadderConstants.PIXEL_PER_METER != fishermanX) {
            if (getX() / SalmonLadderConstants.PIXEL_PER_METER < fishermanX) {
                Timer.schedule(movement.get(1), 0, 1 / 400f, Math.abs((int) (getX() - (fishermanX * SalmonLadderConstants.PIXEL_PER_METER))));
            } else {
                Timer.schedule(movement.get(3), 0, 1 / 400f, Math.abs((int) (getX() - (fishermanX * SalmonLadderConstants.PIXEL_PER_METER))));
            }
        }
        if (getY() / SalmonLadderConstants.PIXEL_PER_METER != fishermanY + 1) {
            if (getY() / SalmonLadderConstants.PIXEL_PER_METER < fishermanY + 1) {
                Timer.schedule(movement.get(0), 0, 1 / 400f, Math.abs((int) (getY() - (fishermanY * SalmonLadderConstants.PIXEL_PER_METER + 32))));
            } else {
                Timer.schedule(movement.get(2), 0, 1 / 400f, Math.abs((int) (getY() - (fishermanY * SalmonLadderConstants.PIXEL_PER_METER + 32))));
            }
        }
    }

    private boolean noTaskScheduled() {
        return !movement.get(0).isScheduled() && !movement.get(1).isScheduled() && !movement.get(2).isScheduled() && !movement.get(3).isScheduled();
    }
}