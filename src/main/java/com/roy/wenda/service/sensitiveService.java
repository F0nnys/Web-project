package com.roy.wenda.service;

import com.roy.wenda.controller.QuestionController;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class sensitiveService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(sensitiveService.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader read = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(read);
            String linText;
            while((linText = bufferedReader.readLine()) != null){
                addWord(linText.trim());
            }
            read.close();
        }catch (Exception e){
            logger.error("读取敏感词文件失败"+e.getMessage());
        }
    }

    private void addWord(String lineText){
        TrieNode tempNode = rootNode;
        for(int i=0;i<lineText.length();i++){
            Character k = lineText.charAt(i);
            TrieNode node = tempNode.getSubNode(k);
            if(node == null){
                node = new TrieNode();
                tempNode.addSubNode(k,node);
            }
            tempNode = node;
            if(i==lineText.length()-1){
                tempNode.setKeywordEnd(true);
            }
        }
    }

    private class TrieNode {
        private boolean end = false;
        private Map<Character, TrieNode> subNodes = new HashMap<>();
        public void addSubNode(Character key,TrieNode node){
            subNodes.put(key,node);
        }
        TrieNode getSubNode(Character key){
            return subNodes.get(key);
        }
        boolean isKeyWordEnd(){return end;}
        void setKeywordEnd(boolean end){
            this.end = end;
        }
    }

    private TrieNode rootNode = new TrieNode();

    private boolean isSymbol(char c){
        int num = (int) c;
        return !CharUtils.isAsciiAlphanumeric(c) && (num< 0x2E80 || num > 0x9FFF);
    }

    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return text;
        }
        StringBuilder sb = new StringBuilder();
        String replacement = "***";
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;
        while(position<text.length()){
            char c = text.charAt(position);
            if(isSymbol(c)){
                if(tempNode == rootNode){
                    sb.append(c);
                    begin++;
                }
                position++;
                continue;
            }
            tempNode = tempNode.getSubNode(c);
            if(tempNode == null){
                sb.append(text.charAt(begin));
                position = begin+1;
                begin = position;
                tempNode = rootNode;
            }else if(tempNode.isKeyWordEnd()){
                sb.append(replacement);
                position = position+1;
                begin = position;
                tempNode = rootNode;
            }else {
                ++position;
            }
        }
        sb.append(text.substring(begin));
        return sb.toString();
    }
    public static void main(String[] args){
        sensitiveService s = new sensitiveService();
        s.addWord("色情");
        s.addWord("赌博");
        s.addWord("李泽杨");
        System.out.println(s.filter("你好李 泽杨"));
    }
}
