package com.github.stairch.rest;

import com.github.stairch.dtos.PointDTO;
import com.github.stairch.dtos.SnakeDTO;
import com.github.stairch.types.Move;

import java.util.ArrayList;
import java.util.List;

public class PathFinderReworked {

    private int fieldWidth;
    private int fieldHeight;
    private boolean xTried = false;
    private boolean yTried = false;

    public PathFinderReworked(final int fieldWidth, final int fieldHeight) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

    }

    public SnakeDTO getOwnSnake(String ownSnake, List<SnakeDTO> allSnakes) {
        for (SnakeDTO snake : allSnakes) {
            if (snake.getId().equals(ownSnake)) {
                return snake;
            }
        }
        return null;
    }

    public PointDTO getClosestFood(SnakeDTO ownSnake, List<PointDTO> Foods) {
        int listLength = Foods.size();
        int lessDiff = 5000000;
        PointDTO closestFood;
        PointDTO foodNow;

        closestFood = Foods.get(0);

        if (listLength > 1) {
            //get x,y Coordinates from ownSnake
            PointDTO ownSnakeHeadPosition = ownSnake.getCoordinates().get(0);
            int ownSnakeX = ownSnakeHeadPosition.getX();
            int ownSnakeY = ownSnakeHeadPosition.getY();
            for (int i = 0; i < listLength; i++) {
                //Get x,y coordinates from food
                PointDTO food = Foods.get(i);
                int foodX = food.getX();
                int foodY = food.getY();

                //Calc how close
                int diffX = ownSnakeX - foodX;
                int diffY = ownSnakeY - foodY;
                if (diffX < 0) {
                    diffX = -diffX;
                }
                if (diffY < 0) {
                    diffY = -diffY;
                }

                int diff = diffY + diffX;
                if (diff < lessDiff) {
                    lessDiff = diff;
                    closestFood = Foods.get(i);
                }
            }
        }
        return closestFood;
    }

    public Move moveToFood(SnakeDTO ownSnake, PointDTO food, List<SnakeDTO> allSnakes) {
        PointDTO head = new PointDTO();
        head.setX(ownSnake.getCoordinates().get(0).getX());
        head.setY(ownSnake.getCoordinates().get(0).getY());

        int xDistance = food.getX() - head.getX();
        int yDistance = food.getY() - head.getY();

        boolean moveRight = xDistance > 0;
        boolean moveDown = yDistance > 0;

        if (xDistance < 0) {
            xDistance *= -1;
        }
        if (yDistance < 0) {
            yDistance *= -1;
        }

        List<Move> priorities = new ArrayList<>();

        if (xDistance > yDistance) {
            if (moveRight) {
                priorities.add(Move.right);
            } else {
                priorities.add(Move.left);
            }
            if (moveDown) {
                priorities.add(Move.down);
            } else {
                priorities.add(Move.up);
            }
        } else {
            if (moveDown) {
                priorities.add(Move.down);
            } else {
                priorities.add(Move.up);
            }
            if (moveRight) {
                priorities.add(Move.right);
            } else {
                priorities.add(Move.left);
            }
        }

        if (!priorities.contains(Move.right)) {
            priorities.add(Move.right);
        }
        if (!priorities.contains(Move.left)) {
            priorities.add(Move.left);
        }
        if (!priorities.contains(Move.down)) {
            priorities.add(Move.down);
        }
        if (!priorities.contains(Move.up)) {
            priorities.add(Move.up);
        }

        for (Move priority : priorities) {
            PointDTO moveTo = new PointDTO();
            moveTo.setX(head.getX());
            moveTo.setY(head.getY());
            switch (priority) {
                case right:
                    moveTo.setX(head.getX() + 1);
                    if (this.pointIsFree(moveTo, allSnakes)) {
                        return Move.right;
                    }
                    break;
                case down:
                    moveTo.setY(head.getY() + 1);
                    if (this.pointIsFree(moveTo, allSnakes)) {
                        return Move.down;
                    }
                    break;
                case left:
                    moveTo.setX(head.getX() - 1);
                    if (this.pointIsFree(moveTo, allSnakes)) {
                        return Move.left;
                    }
                    break;
                case up:
                    moveTo.setY(head.getY() - 1);
                    if (this.pointIsFree(moveTo, allSnakes)) {
                        return Move.up;
                    }
                    break;
            }
        }

        return Move.right;
    }

    public boolean pointIsFree(PointDTO target, List<SnakeDTO> allSnakes) {
        if (target.getX() > this.fieldWidth - 1 ||   //out of Field
                target.getX() < 0 ||
                target.getY() > this.fieldHeight - 1 ||
                target.getY() < 0) {
            return false;
        }
        if (getOccupiedFields(allSnakes).contains(target)) {  //wenn eine lebende Snake auf dem Target Feld ist
            return false;
        }
        return true;
    }

    //TODO: schwanz weg?
    public List<PointDTO> getOccupiedFields(List<SnakeDTO> snakes) {
        List<PointDTO> occupiedPoints = new ArrayList<>();
        for (SnakeDTO snake : snakes) {
            occupiedPoints.addAll(snake.getCoordinates());
        }
        return occupiedPoints;
    }
}
