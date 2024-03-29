/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package automarker;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Annie
 */
public class Solution {
    private static int idCount = 0;

    private int id;
    private Question question;
    private Line [] lines;
    private String dateSubmitted;   //TODO: change to formatted date
    private double mark;
    private int penalty;
    
    public Solution(Question question, Line [] lines){
        idCount += 1;
        id = idCount;
        this.question = question;
        this.lines = lines;
        mark=0;
        penalty=1;
    }

    public int getID(){
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Line [] getLines() {
        return lines;
    }

    //pos=-1 for end position
    public void addLine(Line line, int pos){
        Line [] newLines = new Line[lines.length+1];
        if (pos==-1){
           for (int i=0;i<lines.length;i++){
                newLines[i] = lines[i];
            }
        }else{
            int ind = 0;
            for (int i=0;i<lines.length+1;i++){
                if(i==pos){
                    newLines[ind] = line;
                    ind+=1;
                }
                newLines[ind] = lines[i];
                ind+=1;
            }
        }

    }

    public void setLines(Line []lines) {
        this.lines = lines;
    }

    public String getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(String dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }
    
    public void setMark(int markPen){
        for (Line line : lines) {
            mark+=line.getMark();
        }
        mark = mark;
        if(mark>question.getMaxMark()/markPen){
            mark = question.getMaxMark()/markPen;
        }
        penalty = markPen;
    }

    public double getMark(){
        return mark;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Solution " + id +
                "\nDate Submitted: " + dateSubmitted +
                "\nQuestion: " + question.toString() +
                "\nAnswer: \n");
        for (Line line : lines) {
            sb.append(line).append("\n");
        }
        sb.append("Final mark: ").append(mark).append(" out of ").append(question.getMaxMark()).append("\t penalty: " + (penalty-1));
        return sb.toString();
    }

    public String toStringTechnical(){
        return "Solution{" + "id=" + id + ", question=" + question + ", lines=" + lines + ", dateSubmitted=" + dateSubmitted + '}';
    }

    public String outputFormat(){
        StringBuilder sb = new StringBuilder();
        sb.append("S" + id +
                "\nD" + dateSubmitted +
                "\nQ" + question.outputFormat() +
                "\nA:\n");
        for (Line line : lines) {
            sb.append(line.outputFormat()).append("\n");
        }
        return sb.toString();
    }

}
