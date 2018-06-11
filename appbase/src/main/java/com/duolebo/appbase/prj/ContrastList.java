package com.duolebo.appbase.prj;

import java.util.HashMap;
import java.util.Map;

public class ContrastList {
    public Map<Character, String> maplist = new HashMap<Character, String>();// 摩尔斯编码表集合
    /**
     *    ¤	00000
     *    ç	11111
     *    ´	0000
     *    æ	1111
     *    ­	000
     *    ¢	111
     *    é	00
     *    å	11
     *    è	10
     *    ¡	01
     *    ¨	·1
     *    æ	·0
     *    ®	·
     *    »   ★
     */
    //

    public ContrastList() {
        maplist.put('A', "¡");
        maplist.put('B', "1­");
        maplist.put('C', "èè");
        maplist.put('D', "1é");
        maplist.put('E', "0");
        maplist.put('F', "éè");
        maplist.put('G', "å0");
        maplist.put('H', "´");
        maplist.put('I', "é");
        maplist.put('G', "0¢");
        maplist.put('K', "è1");
        maplist.put('L', "¡é");
        maplist.put('M', "å");
        maplist.put('N', "è");
        maplist.put('O', "¢");
        maplist.put('P', "0å0");
        maplist.put('Q', "å¡");
        maplist.put('R', "0è");
        maplist.put('S', "­");
        maplist.put('T', "1");
        maplist.put('U', "é1");
        maplist.put('V', "­1");
        maplist.put('W', "0å");
        maplist.put('X', "1é1");
        maplist.put('Y', "èå");
        maplist.put('Z', "åé");

        maplist.put('a', "®¡");
        maplist.put('b', "¨­");
        maplist.put('c', "®èè");
        maplist.put('d', "¨é");
        maplist.put('e', "æ");
        maplist.put('f', "®éè");
        maplist.put('g', "®å0");
        maplist.put('h', "®´");
        maplist.put('i', "®é");
        maplist.put('g', "æ¢");
        maplist.put('k', "®è1");
        maplist.put('l', "®¡é");
        maplist.put('m', "®å");
        maplist.put('n', "®è");
        maplist.put('o', "®¢");
        maplist.put('p', "æå0");
        maplist.put('q', "®å¡");
        maplist.put('r', "æè");
        maplist.put('s', "®­");
        maplist.put('t', "¨");
        maplist.put('u', "®é1");
        maplist.put('v', "®­1");
        maplist.put('w', "æå");
        maplist.put('x', "¨é1");
        maplist.put('y', "®èå");
        maplist.put('z', "®åé");

        /* 数字电码¡9 */
        maplist.put('0', "ç");
        maplist.put('1', "0æ");
        maplist.put('2', "é¢");
        maplist.put('3', "­å");
        maplist.put('4', "´1");
        maplist.put('5', "¤");
        maplist.put('6', "1´");
        maplist.put('7', "å­");
        maplist.put('8', "¢é");
        maplist.put('9', "æ0");

        maplist.put(')', "®ç");
        maplist.put('(', "ææ");
        maplist.put('*', "®é¢");
        maplist.put('&', "®­å");
        maplist.put('^', "®´1");
        maplist.put('%', "®¤");
        maplist.put('$', "¨´");
        maplist.put('#', "®å­");
        maplist.put('@', "®¢é");
        maplist.put('!', "®æ0");

        maplist.put('）', "ç®");
        maplist.put('（', "0æ®");
//        maplist.put('*', "®é¢");
//        maplist.put('&&&&', "­å®");
        maplist.put('…', "´1®");
//        maplist.put('%', "®¤");
        maplist.put('￥', "1´®");
//        maplist.put('#######', "®å­");
//        maplist.put('@@@', "®¢é");
        maplist.put('！', "æ0®");

        /* 标点符号，可自增删 */
        maplist.put(',', "åéå"); // ,逗号
        maplist.put('<', "®åéå"); //
        maplist.put('.', "0èè1"); // .句号
        maplist.put('>', "æèè1"); //
        maplist.put('?', "éåé"); // ?问号
        maplist.put('/', "®éåé"); // 斜线
        maplist.put('\\', "èèå"); // 刀符号
        maplist.put('|', "®èèå"); // 竖线
        maplist.put('\'', "0æ0");// '单引号
        maplist.put('\"', "ææ0");// '引号
        maplist.put('=', "1­1");  // =等号
        maplist.put('+', "¨­1");  // +加号
        maplist.put(':', "¢­"); // :冒号
        maplist.put(';', "èèè"); // ;分号

        maplist.put('·', "èå0");  //
        maplist.put('`', "èå0®");  //
        maplist.put('~', "®èå0");  //

        maplist.put(']', "èå¡");  // {前括号
        maplist.put('}', "®èå¡"); // }后括号

        maplist.put('[', "è1é1");  // [前括号
        maplist.put('{', "®è1é1"); // ]后括号


        maplist.put('-', "¡éè");// "减号
        maplist.put('_', "®¡éè"); // _

        /***
         *
         */
        maplist.put('，', "åéå®"); // ,逗号
        maplist.put('《', "®åéå®"); //
        maplist.put('。', "0èè1®"); // .句号
        maplist.put('》', "æèè1®"); //
        maplist.put('？', "éåé®"); // ?问号
        maplist.put('、', "®éåé®"); // 斜线
//        maplist.put('、', "èèå"); // 刀符号
//        maplist.put('|', "®èèå"); // 竖线
        maplist.put('‘', "0æ0®");// '单引号
        maplist.put('“', "ææ0®");// '引号
//        maplist.put('=', "1­1");  // =等号
//        maplist.put('+', "¨­1®");  // +加号
        maplist.put('：', "¢­®"); // :冒号
        maplist.put('；', "èèè®"); // ;分号

        maplist.put('】', "èå¡®");  // {前括号
//        maplist.put('}', "®èå¡"); // }后括号

        maplist.put('【', "è1é1®");  // [前括号
//        maplist.put('{', "®è1é1"); // ]后括号


//        maplist.put('-', "¡éè");// "减号
        maplist.put('—', "®¡éè®"); // _

        maplist.put(' ', "»");      // 留空格，这里的星号是自定义的
    }
}
