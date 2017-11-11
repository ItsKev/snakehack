package com.github.stairch.dtos;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.stream.Collectors;

public final class MoveRequestDTO {

    @SerializedName("game_id")
    private String gameId;
    private int width;
    private int height;

    private String you;
    private int turn;

    private List<SnakeDTO> snakes;
    private List<PointDTO> foods;
    @SerializedName("dead_snakes")
    private List<SnakeDTO> deadSnakes;

    /**
     * Default constructor.
     */
    public MoveRequestDTO() {

    }

    /**
     * @return the gameId
     */
    public final String getGameId() {
        return gameId;
    }

    /**
     * @param gameId the gameId to set
     */
    public final void setGameId(final String gameId) {
        this.gameId = gameId;
    }

    /**
     * @return the width
     */
    public final int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public final void setWidth(final int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public final int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public final void setHeight(final int height) {
        this.height = height;
    }

    /**
     * @return the you
     */
    public final String getYou() {
        return you;
    }

    /**
     * @param you the you to set
     */
    public final void setYou(final String you) {
        this.you = you;
    }

    /**
     * @return the turn
     */
    public final int getTurn() {
        return turn;
    }

    /**
     * @param turn the turn to set
     */
    public final void setTurn(final int turn) {
        this.turn = turn;
    }

    /**
     * @return the snakes
     */
    public final List<SnakeDTO> getSnakes() {
        return snakes;
    }

    /**
     * @param snakes the snakes to set
     */
    public final void setSnakes(final List<SnakeDTO> snakes) {
        this.snakes = snakes;
    }

    /**
     * @return the food
     */
    public final List<PointDTO> getFood() {
        return foods;
    }

    /**
     * @param food the food to set
     */
    public final void setFood(final List<PointDTO> food) {
        this.foods = food;
    }

    public List<SnakeDTO> getDeadSnakes() {
        return deadSnakes;
    }

    public void setDeadSnakes(List<SnakeDTO> deadSnakes) {
        this.deadSnakes = deadSnakes;
    }

    /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#toString()
         */
    @Override
    public String toString() {
        return "MoveRequest [gameId=" + gameId + ", width=" + width + ", height=" + height + ", you=" + you + ", turn="
                + turn + ", snakes=" + snakes + ", food=" + foods + "]";
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((foods == null) ? 0 : foods.hashCode());
        result = prime * result + ((gameId == null) ? 0 : gameId.hashCode());
        result = prime * result + height;
        result = prime * result + ((snakes == null) ? 0 : snakes.hashCode());
        result = prime * result + turn;
        result = prime * result + width;
        result = prime * result + ((you == null) ? 0 : you.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MoveRequestDTO other = (MoveRequestDTO) obj;
        if (foods == null) {
            if (other.foods != null)
                return false;
        } else if (!foods.equals(other.foods))
            return false;
        if (gameId == null) {
            if (other.gameId != null)
                return false;
        } else if (!gameId.equals(other.gameId))
            return false;
        if (height != other.height)
            return false;
        if (snakes == null) {
            if (other.snakes != null)
                return false;
        } else if (!snakes.equals(other.snakes))
            return false;
        if (turn != other.turn)
            return false;
        if (width != other.width)
            return false;
        if (you == null) {
            if (other.you != null)
                return false;
        } else if (!you.equals(other.you))
            return false;
        return true;
    }

}