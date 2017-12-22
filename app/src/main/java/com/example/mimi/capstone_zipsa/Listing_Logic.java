package com.example.mimi.capstone_zipsa;

/**
 * Created by guest on 2017. 11. 25..
 */

class Listing_Logic {
    public static String main(String test_message){


        String return_message = "";

        if(test_message.contains("택배")){
            return_message = "문 앞에 놔주세요";
        }else if(test_message.contains("배달")){
            return_message = "저희 배달 시킨적 없습니다";
        }else{
            return_message = "아직 미정입니다.";
        }



        return return_message;
    }
}
