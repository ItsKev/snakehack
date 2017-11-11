package com.github.stairch.rest;

import com.github.stairch.dtos.PointDTO;
import com.github.stairch.dtos.SnakeDTO;
import com.github.stairch.types.Move;

import java.util.ArrayList;
import java.util.List;

public class PathFinderDaniel {

    private int fieldWidth;
    private int fieldHeight;
    private boolean xTried = false;
    private boolean yTried = false;

    public PathFinderDaniel(final int fieldWidth, final int fieldHeight){
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

    public Move moveToFood(SnakeDTO ownSnake, PointDTO Food, List<SnakeDTO> allSnakes) {
        List<Move> movePrio = new ArrayList<>();
        List<Move> movePossibilitys = new ArrayList<>();
        movePossibilitys.add(Move.right);
        movePossibilitys.add(Move.down);
        movePossibilitys.add(Move.left);
        movePossibilitys.add(Move.up);

        PointDTO ownHead = ownSnake.getCoordinates().get(0);

        if(ownHead.getY()>Food.getY()){ //move up
            movePrio.add(Move.up);
        }
        else if(ownHead.getY()<Food.getY()){ //move down
            movePrio.add(Move.down);
        }
        if(ownHead.getX()>Food.getX()){ //move left
            movePrio.add(Move.left);
        }
        else if(ownHead.getX()<Food.getX()){ //move right
            movePrio.add(Move.right);
        }

        for(Move move : movePossibilitys){
            if(!movePrio.contains(move)){
                movePrio.add(move);
            }
        }

        for(Move move : movePrio){
            PointDTO target = new PointDTO();
            target.setX(ownHead.getX());
            target.setY(ownHead.getY());
            if(move.equals(Move.right)){
                target.setX(target.getX()+1);
            }else if(move.equals(Move.left)){
                target.setX(target.getX()-1);
            }else if(move.equals(Move.up)){
                target.setY(target.getY()-1);
            }else{ //down
                target.setY(target.getY()+1);
            }
            if(this.pointIsFree(target, allSnakes)){
                return move;
            }
        }
        return Move.right;  //default (all bad moves)
    }


    public boolean pointIsFree(PointDTO target, List<SnakeDTO> allSnakes){
        if(target.getX() > this.fieldWidth-1 ||   //out of Field
                target.getX() < 0 ||
                target.getY() > this.fieldHeight-1 ||
                target.getY() < 0){
            return false;
        }
        if(getOccupiedFields(allSnakes).contains(target)) {  //wenn eine lebende Snake auf dem Target Feld ist
            return false;
        }
        return true;
    }

    public boolean pointIsClosed(PointDTO target, List<SnakeDTO> allSnakes){

        return false;
    }

    //TODO: schwanz weg?
    public List<PointDTO> getOccupiedFields(List<SnakeDTO> snakes) {
        List<PointDTO> occupiedPoints = new ArrayList<>();
        for (SnakeDTO snake : snakes) {
            for (PointDTO point : snake.getCoordinates()) {
                occupiedPoints.add(point);
            }
        }
        return occupiedPoints;
    }
}
