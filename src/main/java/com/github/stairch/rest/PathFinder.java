package com.github.stairch.rest;

import com.github.stairch.dtos.PointDTO;
import com.github.stairch.dtos.SnakeDTO;
import com.github.stairch.types.Move;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {

    public PathFinder(){

    }

    public SnakeDTO getOwnSnake(String ownSnake, List<SnakeDTO> allSnakes){
        for(SnakeDTO snake : allSnakes){
            if(snake.getId() == ownSnake){
                return snake;
            }
        }
        return null;
    }

    public PointDTO getClosestFood(SnakeDTO ownSnake, List<PointDTO> Foods){
        return null;
    }

    public Move moveToFood(SnakeDTO ownSnake, PointDTO Food){
        return null;
    }

    public boolean pointIsFree(PointDTO target, List<SnakeDTO> allSnakes, int fieldWidth, int fieldHeight){
        if(target.getX() > fieldWidth ||   //out of Field
                target.getX() < 0 ||
                target.getY() > fieldHeight ||
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
            for(PointDTO point : snake.getCoordsAsPoints()){
                occupiedPoints.add(point);
            }
        }
        return occupiedPoints;
    }
}
