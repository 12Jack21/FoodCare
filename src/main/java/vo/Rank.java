package vo;

public class Rank implements Comparable<Rank>{
    //队伍或者运动员的 id
    private int id;
    //排名
    private int rank;
    private String name;
    private Double score;

    public Rank() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public int compareTo(Rank o) {
        return this.getScore().compareTo(o.getScore());
    }
}
