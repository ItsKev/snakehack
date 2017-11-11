package com.github.stairch.rest;

import com.github.stairch.dtos.PointDTO;
import com.github.stairch.dtos.SnakeDTO;
import com.github.stairch.types.Move;

import java.util.List;

public class PathFinder {

    public PathFinder(){

    }

    public SnakeDTO getOwnSnake(String ownSnake, List<SnakeDTO> allSnakes){
        return null;
    }

    public PointDTO getClosestFood(SnakeDTO ownSnake, List<PointDTO> Foods){
        return null;
    }

    public Move moveToFood(SnakeDTO ownSnake, PointDTO Food){
        return null;
    }

    public boolean pointIsFree(PointDTO target, List<SnakeDTO> allSnakes, int fieldWidth, int fieldHeight){

    }
}
