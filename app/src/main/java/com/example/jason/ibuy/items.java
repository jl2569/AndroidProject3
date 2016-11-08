package com.example.jason.ibuy;


public class items {
    private  int need;
    private int id;
    private String name;
    private int amount;

    public items(){
    }
    public items(int id,String name, int amount,int need) {
        this.id=id;
        this.name=name;
        this.amount=amount;
        this.need = need;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAmount(int amount){
        this.amount = amount;
    }
    public void setNeed(int need){
        this.need = need;
    }
    public int getId(){
        return id;
    }
    public int getAmount(){
        return amount;
    }
    public int getNeed(){
        return need;
    }
    public String getName(){
        return name;
    }
}
