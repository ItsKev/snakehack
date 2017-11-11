package com.github.stairch.rest;

import com.github.stairch.dtos.PointDTO;
import com.github.stairch.dtos.SnakeDTO;
import com.github.stairch.types.Move;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {

    private int fieldWidth;
    private int fieldHeight;
    private boolean xTried = false;
    private boolean yTried = false;

    public PathFinder(final int fieldWidth, final int fieldHeight){
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

    }

    public SnakeDTO getOwnSnake(String ownSnake, List<SnakeDTO> allSnakes){
        for(SnakeDTO snake : allSnakes){
            if(snake.getId().equals(ownSnake)){
                return snake;
            }
        }
        return null;
    }

    public PointDTO getClosestFood(SnakeDTO ownSnake, List<PointDTO> Foods){
        int listLength = Foods.size();
        int lessDiff = 5000000;
        PointDTO closestFood;
        PointDTO foodNow;

        closestFood = Foods.get(0);

        if (listLength > 1)
        {
            //get x,y Coordinates from ownSnake
            PointDTO ownSnakeHeadPosition = ownSnake.getCoordinates().get(0);
            int ownSnakeX = ownSnakeHeadPosition.getX();
            int ownSnakeY = ownSnakeHeadPosition.getY();
            for(int i = 0; i < listLength; i++){
                //Get x,y coordinates from food
                PointDTO food = Foods.get(i);
                int foodX = food.getX();
                int foodY = food.getY();

                //Calc how close
                int diffX = ownSnakeX - foodX;
                int diffY = ownSnakeY - foodY;
                if (diffX < 0){
                    diffX = -diffX;
                }
                if (diffY < 0){
                    diffY = -diffY;
                }

                int diff = diffY + diffX;
                if (diff < lessDiff){
                    lessDiff = diff;
                    closestFood = Foods.get(i);
                }
            }
        }
        return closestFood;
    }

    public Move moveToFood(SnakeDTO ownSnake, PointDTO Food, List<SnakeDTO> allSnakes){

        this.xTried = false;
        this.yTried = false;
        PointDTO ownHead = new PointDTO();

        ownHead.setX(ownSnake.getCoordinates().get(0).getX());
        ownHead.setY(ownSnake.getCoordinates().get(0).getY());


        int distX = Food.getX() - ownHead.getX();
        if (distX < 0) {
            distX *= (-1);
        }
        int distY = Food.getY() - ownHead.getY();
        if (distY < 0) {
            distY *= (-1);
        }



        if (distX > distY){
            xTried = true;
            return this.moveX(ownHead,Food,allSnakes);
        }else {
            yTried = true;
            return this.moveY(ownHead, Food, allSnakes);
        }
    }

    private Move moveY(PointDTO ownHead, PointDTO Food, List<SnakeDTO> allSnakes) {
        boolean isPositiv;
        PointDTO nextY = new PointDTO();
        nextY.setX(ownHead.getX());
        nextY.setY(ownHead.getY());
        if (Food.getY() - ownHead.getY() < 0) {
            nextY.setY(ownHead.getY() - 1);
            isPositiv = false;
        } else {
            nextY.setY(ownHead.getY() + 1);
            isPositiv = true;
        }
        boolean pointIsFree = this.pointIsFree(nextY, allSnakes);

        if (isPositiv && pointIsFree) {
            return Move.down;
        } else if (!isPositiv && pointIsFree) {
            return Move.up;
        } else if (this.xTried) {
            if (isPositiv) {
                nextY.setY(ownHead.getY() - 1);
                if (this.pointIsFree(nextY, allSnakes)) {
                    return Move.up;
                } else {
                    nextY.setY(ownHead.getY() + 1);
                    if (this.pointIsFree(nextY, allSnakes)) {
                        return Move.down;
                    }
                }
            }
        }
        return this.moveX(ownHead, Food, allSnakes);
    }






    private Move moveX(PointDTO ownHead, PointDTO Food, List<SnakeDTO> allSnakes) {
        boolean isPositiv;
        PointDTO nextX = new PointDTO();
        nextX.setX(ownHead.getX());
        nextX.setY(ownHead.getY());

        if(Food.getX() - ownHead.getX() < 0){
            nextX.setX(ownHead.getX()-1);
            isPositiv = false;
        }else{
            nextX.setX(ownHead.getX()+1);
            isPositiv = true;
        }
        boolean pointIsFree = pointIsFree(nextX, allSnakes);

        if (isPositiv && pointIsFree){
            return Move.right;
        }else if (!isPositiv && pointIsFree){
            return Move.left;
        }else if (this.yTried) {
            if (isPositiv) {
                nextX.setX(ownHead.getX() - 1);
                if (this.pointIsFree(nextX, allSnakes)) {
                    return Move.left;
                }
            } else {
                nextX.setX(ownHead.getX() + 1);
                if (this.pointIsFree(nextX, allSnakes)) {
                    return Move.right;
                }
            }
        }
        return moveY(ownHead, Food, allSnakes);
    }

    public boolean pointIsFree(PointDTO target, List<SnakeDTO> allSnakes){
        if(target.getX() > this.fieldWidth ||   //out of Field
                target.getX() < 0 ||
                target.getY() > this.fieldHeight ||
                target.getY() < 0){
            return false;
        }
        if(getOccupiedFields(allSnakes).contains(target)) {  //wenn eine lebende Snake auf dem Target Feld ist
            return false;
        }
        return true;
    }

    //TODO: schwanz weg?
    public List<PointDTO> getOccupiedFields(List<SnakeDTO> snakes){
        List<PointDTO> occupiedPoints = new ArrayList<>();
        for(SnakeDTO snake : snakes){
            for(PointDTO point : snake.getCoordinates()){
                occupiedPoints.add(point);
            }
        }
        return occupiedPoints;
    }
}
