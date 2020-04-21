package javabean;

import java.util.ArrayList;

public class field_check {

    public int field_check(ArrayList<String> list,String school,String id,String pwd){
        String regex1 = "^[a-zA-Z][a-zA-Z0-9_]{5,15}$";//只允许字母开头且6位以上
        if(id.matches(regex1)){
            if(pwd.length()>=6){
                for(int i=0;i<list.size();i++){
                    if(list.get(i).equals(school)){
                        //判断输入的内容是否来自下拉列表
                        return 0;
                    }
                }
                return 1;
            }else{ return 2; }
        }else{ return 3; }
    }
}
