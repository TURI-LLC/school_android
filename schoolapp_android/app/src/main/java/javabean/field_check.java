package javabean;

import java.util.ArrayList;

public class field_check {

    public Boolean field_check(ArrayList<String> list,String school,String id,String pwd){
        String regex1 = "^[a-zA-Z][a-zA-Z0-9_]{6,15}$";//只允许字母开头且6位以上
        String regex2="^[a-zA-Z0-9_.]{6,15}$";
        if(id.matches(regex1)){
            if(pwd.matches(regex2)){
                for(int i=0;i<list.size();i++){
                    if(list.get(i).equals(school)){
                        return true;
                    }
                }
                    return false;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
