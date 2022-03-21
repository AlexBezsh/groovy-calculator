package com.epam.ld.groovy

import com.epam.ld.groovy.calculator.Calculator

class Main {

    static void main(String[] args) {
        def expressionToResult = [
                '1+2': '3',
                '2-1': '1',
                '2*1': '2',
                '2/4': '0.5',
                '0/(-3)': '0',
                '-(3+2)+1': '-4',
                '(2+2)*3': '12',
                '2+2*(5-7)': '-2',
                '(1+2+3+4)*2/5': '4',
                '(-(-(-(-(-(-9+3))))))': '6',
                '-2+(-2+(-2)-2*(2+2))': '-14',
                '1+(1+(1+1)*(1+1))*(1+1)+1': '12',
                '(1234+1234*5/10-17-34)/100': '18',
                '(9+(5-(((-(-9+3)-1)+2)-3)))': '10',
                '-1+10/20*3*3/9*2/5/(10/20*3*3/9*2/5)+1': '1',
                '-2-(-2-1-(-2)-(-2)-(-2-2-(-2)-2)-2-2)': '-3',
                '-1024/(-4/(-2))/(-2)/(-2)/(-2)/(-2)/(-2)/(-2)': '-8',
                '-1+2-3+4-5+6-7+8-9+10-11+12-13+14-15+16-17+18-19+20/2': '0',
                '2*2+1-3*1+6/2-3/1-1+55/5-3*3-2*2+2-1/2+1-2+3*2-15/10+2*2': '8',
                '2*2+(1-(3*1+(6/2-(3/1-1+(55/5-3*(3-2*(2+2)-1/2)+1-2+3)*2))))-15/10+2*2': '62.5',
                '10*10-(100/10)+10+1000*1000*1000*1000*1000*1000*1000*1000*1000': '1000000000000000000000000100',
                '11111111111111111111111111111111+11111111111111111111111111111111': '22222222222222222222222222222222',
                '999999999999999999999999999999999999999999999999/9': '111111111111111111111111111111111111111111111111'
        ]
        expressionToResult.each {
            assert Calculator.calculate(it.key) == it.value
        }
    }

}
