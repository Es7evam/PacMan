/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import utils.Consts;

/**
 *
 * @author wln
 */
public class Graph {
    private ArrayList<Node> verts;
    private ArrayList<Node> aux;
    private class Node{
        int value;
        boolean flag;
        ArrayList <Node> adjs;
        
        public Node(int value){
            this.value = value;
            flag = false;
            adjs = new ArrayList<>();
        }
        
        public int getValue(){
            return value;
        }
        
        public boolean getFlag(){
            return flag;
        }
        
        public void setFlag(boolean value){
            flag = value;
        }
        
        public void addLink(Node node){
            adjs.add(node);
        }
        
        public void removeLink(Node node){
            if(adjs.contains(node))
                adjs.remove(node);
        }
        
        public int getGrade(){
            return adjs.size();
        }
        
        public Node getAdj(int i){
            return adjs.get(i);
        }
        
        public boolean existAdj(int value){
            for (int i=0; i<adjs.size(); i++){
                if (adjs.get(i).getValue() == value)
                    return true;
            }
            return false;
        }
    }
    
    public Graph(){
        verts = new ArrayList<>();
        aux = new ArrayList<>();
    }
    
    public boolean existNode(int value){
        for (int i=0; i<verts.size(); i++){
            if (verts.get(i).getValue() == value)
                return true;
        }
        return false;
    }
    
    private Node findNode(int value){
        for (int i=0; i<verts.size(); i++){
            if (verts.get(i).getValue() == value)
                return verts.get(i);
        }
        return null;
    }
    
    private Node getVertex(int value){
        for (int i=0; i<verts.size(); i++){
            if (verts.get(i).getValue() == value)
                return verts.get(i);
        }
        return null;
    
    }
    
    public void addVertex(Integer value){
        if(!existNode(value)){
            Node v = new Node(value);
            verts.add(v);
        }
    }
    
    public void addLink(int v1, int v2){
        if(!existNode(v1))
            addVertex(v1);
        
        if(!existNode(v2))
            addVertex(v2);
        
        getVertex(v1).addLink(findNode(v2));
        getVertex(v2).addLink(findNode(v1));
    }
    
    public void removeLink(int v1, int v2){
        if(!existNode(v1))
            addVertex(v1);
        
        if(!existNode(v2))
            addVertex(v2);
        
        if(existLink(v1,v2)){
            getVertex(v1).removeLink(findNode(v2));
            getVertex(v2).removeLink(findNode(v1));
        }
    }
    
    public boolean existLink(Integer v1, Integer v2){
        return existNode(v1) && getVertex(v1).existAdj(v2);
    }
    
    public int getVortexCount(){
        return verts.size();
    }
    
    public void printGraph(){
        for (int i=0; i<verts.size(); i++){
            Node v = verts.get(i);
            System.out.println(v.getValue());
            for (int j=0; j<v.getGrade(); j++)
                System.out.println("  " + v.getAdj(j).getValue());
        }
    }
    
    public String minWay(int myVert, int tVert, int[] link) {
        removeLink(link[0], link[1]);
        String way = bfs(findNode(myVert), findNode(tVert));
        addLink(link[0], link[1]);
        
        return new StringBuilder(way).reverse().toString();
    }
    
    private String bfs(Node origin, Node destiny){
        aux.add(origin);
	origin.setFlag(true);
        int[] reg = new int[verts.size()];
        for (int i=0; i<reg.length; i++){
            reg[i] = -1;
            if(verts.get(i).getFlag())
                verts.get(i).setFlag(false);
        }
            
	while (!aux.isEmpty()){
            Node element=aux.get(0);
            aux.remove(element);
            for (int i = 0; i < element.getGrade(); i++) {
		Node n = element.getAdj(i);
		if(n!=null && !n.getFlag()){
                    aux.add(n);
                    reg[verts.indexOf(n)] = verts.indexOf(element);
                    n.setFlag(true);
                }
            }
            
            if(element == destiny)
                aux.clear();
        }
        String way = new String();
        int index = verts.indexOf(destiny), cont = 0;
        while (index!=verts.indexOf(origin) && cont<verts.size()){
            if(index!=-1 && reg[index] != -1){
                if(verts.get(reg[index]).getValue() == verts.get(index).getValue() + 1)
                    way += "1";
                else if(verts.get(reg[index]).getValue() == verts.get(index).getValue() - 1)
                    way += "2";
                else if(verts.get(reg[index]).getValue() == verts.get(index).getValue() + Consts.NUM_CELLS[0])
                    way += "3";
                else if(verts.get(reg[index]).getValue() == verts.get(index).getValue() - Consts.NUM_CELLS[0])
                    way += "4";
                
                index = reg[index];
            }else
                //System.out.println(origin.getValue());
            cont++;
        }
        return way;
    }
}
