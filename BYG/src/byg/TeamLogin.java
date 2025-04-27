package byg;

public class TeamLogin {
    private String name;
    private String memberOne;
    private String memberTwo;
    private String memberThree;
    
    public TeamLogin(String name, String memberOne, String memberTwo, String memberThree){
        this.name = name;
        this.memberOne = memberOne;
        this.memberTwo = memberTwo;
        this.memberThree = memberThree;
    }
    
    public String name(){
        return this.name;
    }
    public String memberOne(){
        return this.memberOne;
    }
    public String memberTwo(){
        return this.memberTwo;
    }
    public String memberThree(){
        return this.memberThree;
    }
}
