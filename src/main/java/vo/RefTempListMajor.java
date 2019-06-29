package vo;

public class RefTempListMajor {
    private Referee referee;
    private Double score;
    private Integer tempScore_id;
    private Integer isValid;

    public RefTempListMajor() {
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getTempScore_id() {
        return tempScore_id;
    }

    public void setTempScore_id(Integer tempScore_id) {
        this.tempScore_id = tempScore_id;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
