package com.github.stairch.rest;

import com.github.stairch.dtos.PointDTO;
import com.github.stairch.dtos.SnakeDTO;
import com.github.stairch.types.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        PointDTO ownHead = ownSnake.getCoordinates().get(0);

        PointDTO targetUP = new PointDTO();
        targetUP.setX(ownHead.getX());
        targetUP.setY(ownHead.getY()-1);
        int up = pointRoomToMove(targetUP,allSnakes);
        System.out.println("Up: " +up);
        PointDTO targetDown = new PointDTO();
        targetDown.setX(ownHead.getX());
        targetDown.setY(ownHead.getY()+1);
        int down = pointRoomToMove(targetDown,allSnakes);
        System.out.println("Down: " +down);
        PointDTO targetRight = new PointDTO();
        targetRight.setX(ownHead.getX()+1);
        targetRight.setY(ownHead.getY());
        int right = pointRoomToMove(targetRight,allSnakes);
        System.out.println("Right: " + right);
        PointDTO targetLeft = new PointDTO();
        targetLeft.setX(ownHead.getX()-1);
        targetLeft.setY(ownHead.getY());
        int left = pointRoomToMove(targetLeft,allSnakes);
        System.out.println("Left: " + left);

        int max = Math.max(up, Math.max(down, Math.max(left, right)));
        if(up == max){
            movePossibilitys.add(Move.up);
        }
        if(down == max){
            movePossibilitys.add(Move.down);
        }
        if(right == max){
            movePossibilitys.add(Move.right);
        }
        if(left == max){
            movePossibilitys.add(Move.left);
        }


        if(ownHead.getY()>Food.getY()){ //move up
            if(movePossibilitys.contains(Move.up)) {
                movePrio.add(Move.up);
            }
        }
        else if(ownHead.getY()<Food.getY()){ //move down
            if(movePossibilitys.contains(Move.down)) {
                movePrio.add(Move.down);
            }
        }
        if(ownHead.getX()>Food.getX()){ //move left
            if(movePossibilitys.contains(Move.left)) {
                movePrio.add(Move.left);
            }
        }
        else if(ownHead.getX()<Food.getX()){ //move right
            if(movePossibilitys.contains(Move.right)) {
                movePrio.add(Move.right);
            }
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

    public int pointRoomToMove(PointDTO target, List<SnakeDTO> allSnakes){
        if(!pointIsFree(target,allSnakes)){
            return 0;
        }

        List<PointDTO> usedPoints = getOccupiedFields(allSnakes);
        List<PointDTO> pointsOfTargetRoom = new ArrayList<>();
        pointsOfTargetRoom.add(target);

        PointDTO toCheck = new PointDTO();

        boolean changed = true;
        while(changed){
            changed = false;
            for(int x = 0; x < fieldWidth; x++){
                for(int y = 0; y < fieldHeight; y++){
                    toCheck.setX(x);
                    toCheck.setY(y);
                    if(!usedPoints.contains(toCheck)){
                        if(!pointsOfTargetRoom.contains(toCheck)){
                            boolean isneigbour = false;
                            for(PointDTO point : pointsOfTargetRoom){
                                if(isNeigbour(point, toCheck)){
                                    isneigbour = true;
                                }
                            }
                            if(isneigbour){
                                changed = true;
                                PointDTO add = new PointDTO();
                                add.setY(toCheck.getY());
                                add.setX(toCheck.getX());
                                pointsOfTargetRoom.add(add);
                            }
                        }
                    }
                }
            }
        }
        return pointsOfTargetRoom.size();
    }

    private boolean isNeigbour(PointDTO n1, PointDTO n2){
        if(n1.getX() == n2.getX()){
            if(n1.getY() == n2.getY()+1 || n1.getY() == n2.getY()-1){
                return true;
            }
        }else if(n1.getY() == n2.getY()) {
            if (n1.getX() == n2.getX() + 1 || n1.getX() == n2.getX() - 1) {
                return true;
            }
        }
        return false;
    }

    //TODO: schwanz weg?
    public List<PointDTO> getOccupiedFields(List<SnakeDTO> snakes) {
        List<PointDTO> occupiedPoints = new ArrayList<>();
        for (SnakeDTO snake : snakes) {
            for (PointDTO point : snake.getCoordinates()) {
                if(!point.equals(snake.getCoordinates().get(snake.getCoordinates().size()-1))) { //TODO: wenn mehrmals dann doch?
                    occupiedPoints.add(point);
                }
            }
        }
        return occupiedPoints;
    }
}
