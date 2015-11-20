package com.example;

import java.util.Random;

public class JokeLibClass {
    public String GetJoke(){
        int num = jokes.length;
        Random rn = new Random();
        int i = rn.nextInt(num);

        return jokes[i];
    }

    String[] jokes = {
            "Q: What do you call a parade of rabbits hopping backwards?\n\nA: a receding hare-line."
            ,"Q: What do you call an old snowman?\n\nA: Water!"
            ,"Why do artists constantly feel cold\n\nBecause they’re surrounded by drafts."
            ,"Q: Why do gorillas have big nostrils?\n\nA: Because they have big fingers!"
            ,"Q: What do you call a sleeping bull?\n\nA: A bull-dozer."
            ,"Q: Why are teddy bears never hungry?\n\nA: They are always stuffed! "
};
}
