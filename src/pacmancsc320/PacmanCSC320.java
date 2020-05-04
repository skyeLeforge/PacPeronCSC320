package pacmancsc320;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Comparator;
import java.util.PriorityQueue;
import javax.swing.*;

class Node{
    //gonan create tons of this object for the a star algorithim
    int x;
    int y;
    int h;
    int g;
    int lastX;
    int lastY;
    int f;
    
    
    
    //setters
    public void setX(int newx){
        x = newx;   
    }
    
    public void setY(int newy){
        y = newy;   
    }
    
    public void setH(int newh){
        h = newh;   
    }
    
    public void setG(int newg){
        g = newg;   
    }
    
    public void setlastX(int newx){
        lastX = newx;   
    }
    
    public void setlastY(int newy){
        lastY = newy;   
    }
    
    public void setAll(int newx, int newy, int newg, int newh, int lstX, int lstY){
        x = newx;
        y = newy;
        g = newg;
        h = newh;
        f = getF();
        lastX = lstX;
        lastY = lstY;
}
    
    public int getF(){
        return g+h;
    }
    
    
}

class Painting extends JPanel implements KeyListener{
    
    ImageIcon player = new ImageIcon("IMGFiles//pacmanCSC320.png");
    ImageIcon ghost =  new ImageIcon("IMGFiles//ghostCSC320.png");
    static int width =70;
    static int heigth = 70;
    //I initalized this array this way initally just so it doesn't break
    int[][] squares = { {0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0}, 
                        {0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0}, 
                        {0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0}, 
                        {0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0}, 
                        {0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0} 
    };
    
    public Painting(){
        this.setFocusable(true);
        this.addKeyListener(this);
    }
    
    public void copyArray(int[][] newArray){
        for (int i = 0; i < squares.length; i++) {
            System.arraycopy(newArray[i], 0, squares[i], 0, 10);
        }
    }
    
    //this method will draw the board and every entity on the correct location
    public void paint(Graphics g){
        
    //first draw the background    
    g.drawRect(0, 0, 750, 750);
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, 750, 750);
    
    
    //check if the player has won
    if(squares[0][0] == 100){
        Font f = new Font("ComicSansMS", Font.BOLD, 50);
        g.setFont(f);
        g.setColor(Color.WHITE);
        g.drawString("YOU ARE WIN PLAYER", 70, 350);
    }else if(squares[0][0] == -100){ //check if they have lost
        Font f = new Font("ComicSansMS", Font.BOLD, 50);
        g.setFont(f);
        g.setColor(Color.RED);
        g.drawString("YOU ARE LOSE PLAYER", 60, 350);
        
    }else{
        //then draw each element of the array
            for (int i = 0; i < squares.length; i++) {
                for (int j = 0; j < squares.length; j++) {


                    switch (squares[i][j]) {
                        case 4:
                            //food
                            drawFood(g, i, j);
                            break;
                        case 0:
                            //blank
                            emptyCell(g, i, j);
                            break;
                        case 2:
                            //ghost
                            drawGhost(g, i, j);
                            break;
                        case 1:
                            //player
                            drawPlayer(g, i, j);
                            break;
                        default:
                            //wall
                            drawWall(g, i, j);
                            break;
                    }
                }
            }
    }
       
       
    }
    
    // these handle drawing each iddividual type of thing
    public void drawPlayer(Graphics g, int i, int j){
        emptyCell(g, i, j);
        g.drawImage(player.getImage(), i*width, j*width, width, heigth, null);
    }
    
    public void drawGhost(Graphics g, int i, int j){
        emptyCell(g, i, j);
        g.drawImage(ghost.getImage(), i*width, j*width, width, heigth, null);
    }
    
    public void drawFood(Graphics g, int i, int j){
        
        g.drawOval(i*width, j*width, width, heigth);
        g.setColor( Color.WHITE);
        g.fillOval(i*width, j*width, width, heigth);
    }
    
    public void emptyCell(Graphics g, int i, int j){
        g.drawRect(i*width, j*width, width, heigth);
        g.setColor( Color.BLACK);
        g.fillRect(i*width, j*width, width, heigth);
    }
    
    public void drawWall(Graphics g, int i, int j){
        emptyCell(g, i, j);
        g.drawRect(i*width, j*width, width, heigth);
        g.setColor( Color.BLUE);
        g.fillRect(i*width, j*width, width, heigth);
    }
    
    //call this when we want to redraw, takes in the array of locations so that we can figre out what to draw where
    public void redraw(int[][] newArray){
        copyArray(newArray);
        this.repaint();
    }

    
    

    @Override
    public void keyPressed(KeyEvent ke) {
        //we will use the standard gaming set up "WASD" to move pacman
        if(ke.getKeyCode() == KeyEvent.VK_D && squares[0][0] !=100 && squares[0][0] != -100){
            PacmanCSC320.moveP(4);
        }
        
        if(ke.getKeyCode() == KeyEvent.VK_S && squares[0][0] !=100 && squares[0][0] != -100){
            PacmanCSC320.moveP(1);
        }
        
        if(ke.getKeyCode() == KeyEvent.VK_A && squares[0][0] !=100 && squares[0][0] != -100){
            PacmanCSC320.moveP(3);
        }
        
        if(ke.getKeyCode() == KeyEvent.VK_W && squares[0][0] !=100 && squares[0][0] != -100){
            PacmanCSC320.moveP(2);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       // breaks if this method is not here
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //breaks if this method is not here
    }
    
    
        
    }


public class PacmanCSC320{

    //set up all the class variables
    final static int bheight = 10;
    final static int bwidth = 10;
    static int[][] board = new int [bwidth][bheight];
    
    //array of nodes of locations so that we can connect them
    static Node[][] nodes = new Node[bwidth][bheight];
    
    //put these in the array at the starting positions
    final static int player = 1;
    final static int ghost = 2;
    final static int empty = 0;
    final static int wall = 3;
    final static int food = 4;
    int d; //direction an entity moves, 1 is up, 2 is down, 3 is left, 4 is right
    
    //starting location for player, will update as player moves
    static int x = 0;
    static int y = 0;
    
    //starting location for the ghost
    static int gX = bwidth - 1;
    static int gY = bheight - 1;
    //if on food is true populate the square its leaving with a food
    static boolean onFood = false;
   
    //thats the starting number of food on the board, we will use this to determine if you have won or not
    static int numFood = 91;
    //the window that will be displayed and what is displayed in it
    //and what we need for the graphics
    static JFrame frame = new JFrame("Pac-Person");
    static Painting paint = new Painting();
        
    
    
    //now variables that refrence board size
    final static int width = 700;
    final static int height = 700;
    final static int boxWidth = width/bwidth;
    final static int boxHeight = height/bheight;
    
    
    //this will be the explored graph
    //we use it to see if we have explored a square so we dont backtrack
    static boolean[][] explored = new boolean[bwidth][bheight];
    //sets up the game baord
    void setUp (){
        //first fill everything with food
        for (int i = 0; i < bwidth; i++) {
            for (int j = 0; j < bheight; j++) {
                board[i][j] = food;
            }
        }
        
        //put the player, ghost and walls
        board[0][0] = player;
        board[bwidth-1][bheight-1] = ghost;
        
        for (int i = 4; i < 8; i++) {
            board[4][i] = wall;
            board[i][4] = wall;
        }
        
        //create a node for every point on the graph
        nodes = new Node[bwidth][bheight];
        for (int i = 0; i < bwidth; i++) {
            for (int j = 0; j < bheight; j++) {
                nodes[i][j] = new Node();
                nodes[i][j].setX(i);
                nodes[i][j].setY(j);
            }
        }
        
        //fill explored with false
        for (int i = 0; i < bwidth; i++) {
            for (int j = 0; j < bheight; j++) {
                explored[i][j] = false;
            }
        }
        setUpGraphics();
    }
    
    
    //set up the inital graphics
    void setUpGraphics(){
        
        frame.add(paint);
        frame.setSize(width+20, height+50);
        frame.setLocation(0,0);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //now call repaint
        paint.redraw(board);
    }
    //call this whne the player loses
    static void gameLoss(){
        board[0][0] = -100;
        paint.redraw(board);
    }
    
    //handles a ghost movement
    //1 is up, 2 is down, 3 is left, 4 is right
   static void moveG(int newX, int newY){
            if(newY>gY){
                if(gY<bheight-1){
                    switch (board[gX][gY+1]) {
                        case player:
                            gameLoss();
                            break;
                        case wall:
                            //do nothing
                            break;
                        case food:
                            if(onFood){
                                board[gX][gY] = food;
                                board[gX][gY+1] = ghost;
                                gY++;
                                paint.redraw(board);
                            }else{
                                onFood = true;
                                board[gX][gY] = empty;
                                board[gX][gY+1] = ghost;
                                gY++;
                                paint.redraw(board);
                            }

                            break;
                        default:
                            board[gX][gY] = empty;
                            board[gX][gY+1] = ghost;
                            gY++;
                            paint.redraw(board);
                            break;
                            
                        }
                }
                }else if(newY<gY){

                if(gY>0){
                    switch (board[gX][gY-1]) {
                        case player:
                            gameLoss();
                            break;
                        case wall:
                            //do nothing
                            break;
                        case food:
                            if(onFood){
                                board[gX][gY] = food;
                                board[gX][gY-1] = ghost;
                                gY--;
                                paint.redraw(board);
                            }else{
                                onFood = true;
                                board[gX][gY] = empty;
                                board[gX][gY-1] = ghost;
                                gY--;
                                paint.redraw(board);
                            }

                            break;
                        default:
                            board[gX][gY] = empty;
                            board[gX][gY-1] = player;
                            gY--;
                            paint.redraw(board);
                            break;
                        }
                    }
                }else if(newX<gX){
                if(gX>0){
                     switch (board[gX-1][gY]) {
                        case player:
                            gameLoss();
                            break;
                        case wall:
                            //do nothing
                            break;
                        case food:
                            if(onFood){
                                board[gX][gY] = food;
                                board[gX-1][gY] = ghost;
                                gX--;
                                paint.redraw(board);
                            }else{
                                onFood = true;
                                board[gX][gY] = empty;
                                board[gX-1][gY] = ghost;
                                gX--;
                                paint.redraw(board);
                            }

                            break;
                        default:
                            board[gX][gY] = empty;
                            board[gX-1][gY] = player;
                            gX--;
                            paint.redraw(board);
                            break;
                        }
                    }
                }else{
                if(gX<bwidth-1){
                    switch (board[gX+1][gY]) {
                        case player:
                            gameLoss();
                            break;
                        case wall:
                            //do nothing
                            break;
                            case food:
                                if(onFood){
                                board[gX][gY] = food;
                                board[gX+1][gY] = ghost;
                                gX++;
                                paint.redraw(board);
                            }else{
                                onFood = true;
                                board[gX][gY] = empty;
                                board[gX+1][gY] = ghost;
                                gX++;
                                paint.redraw(board);
                            }

                            break;                            
                        default:
                            board[gX][gY] = empty;
                            board[gX+1][gY] = player;
                            gX++;
                            paint.redraw(board);
                            break;
                        }
                    }
                }
               
        
   
   }
   
   static int manhattan(int newX, int newY){
       //this is our heuristic
       //we take the absolute value of the distance between the x and the y
       int man = Math.abs(newX-x)+Math.abs(newY-y);
       return man;
   }
    
   //1 is up, 2 is down, 3 is left, 4 is right
   //sees if a move is valid for the astar algo, if it isnt we dont calculate it
   static boolean checkIfValid(int x, int y, int d){

       if(d == 1 && ((y==bheight-1 || board[x][y+1]==wall) || explored[x][y+1] ))
           return false;       
       if( d == 2 && ((y==0 || board[x][y-1] == wall) || explored[x][y-1] ))
           return false;      
       if(d == 3 && ((x == 0 || board[x-1][y]==wall)|| explored[x-1][y]))
           return false;
       if(d==4 && ((x == bwidth-1 || board[x+1][y]==wall) || explored[x+1][y]))
           return false;
       
       return true;
   }
   static void aStarAlgo(){
       //gave these values just for testing
       int tempX = 0;
       int tempY = 0;
       
  
       //set up our first node
               
       nodes[gX][gY].setAll(gX, gY, 0, manhattan(gX, gY), -1, -1);
     
      //set up a priority queue
      PriorityQueue<Node> pq = new PriorityQueue<>((Node node1, Node node2) -> {
          if( node1.getF()>node2.getF())
              return 1;
          return -1;
       });
       
       //put current node on top of queue
       pq.add(nodes[gX][gY]);
       //while loop
       Node currentNode = pq.peek();
      do{
       //goes untill node where player is at top of queue or queue empty
            //set lastX and lastY based off of the new top node
            currentNode = pq.peek();
            //for each direction

                for (int i = 1; i < 5; i++) {
                    //see if its valid
                    
                    
                    boolean isValid = checkIfValid(currentNode.x, currentNode.y, i);
                    if(isValid){
                        tempX = currentNode.x;
                        tempY=  currentNode.y;
                        //set up a temporary x and y
                        //using switches like this is cleaner than how it is 
                        //done elsehwere in the code, other parts will be 
                        //refactored to this when i have time
                        switch (i) {
                            case 1:
                                tempY++;
                                break;
                            case 2:
                                tempY--;
                                break;
                            case 3:
                                tempX--;
                                break;
                            default:
                                tempX++;
                                break;
                        }
                        
                        nodes[tempX][tempY].setlastX(currentNode.x);
                        nodes[tempX][tempY].setlastY(currentNode.y);
                
                       
                        
                        // calculate num of nodes moved + heuristic
                        nodes[tempX][tempY].setH(manhattan(tempX, tempY));
                        
                        //for the g, its the last nodes g + 1
                        nodes[tempX][tempY].setG(1 + nodes[nodes[tempX][tempY].lastX][nodes[tempX][tempY].lastY].g);
                        
                        
                        //force it to calculate f
                        nodes[tempX][tempY].getF();
                        //place the current node on the priority queue
                        pq.add(nodes[tempX][tempY]);
                    }
            
                }
                //pop top element off of queue before the new adds
                explored[currentNode.x][currentNode.y] = true;
                pq.remove(currentNode);

                
                //System.out.println("pq.peek().x is "+pq.peek().x +" and pq.peek().y is "+pq.peek().y);
                
            } while( !((currentNode.x == x) && (currentNode.y== y)));//end of while
              
              
           //trace back the path we took
           int previousX = currentNode.lastX;
           int tempPX;
           int x3 = 0;
           int previousY = currentNode.lastY;
           int tempPY;
           int y3 = 0;
           boolean moreToGo = true;
           
           while(moreToGo){
               tempPX = nodes[previousX][previousY].lastX;
               tempPY = nodes[previousX][previousY].lastY;
               if(tempPX != -1){
                   x3 = previousX;
                   y3 = previousY;
                   previousX = tempPX;
                   previousY = tempPY;
                   
                   
               }else{
                   moreToGo = false;
               }

           }
           //clear explored
           for (int i = 0; i < bwidth; i++) {
               for (int j = 0; j < bheight; j++) {
                   explored[i][j] = false;
               }
       }
       
           //move the ghost to the new specified location
           moveG(x3, y3);
           
    }
   
    //handles a player movement
    static void moveP(int d) {
        switch (d) {
            case 1:
                if(y<bheight-1){
                    switch (board[x][y+1]) {
                        case ghost:
                            gameLoss();
                            break;
                        case wall:
                            //do nothing
                            break;
                        case food:
                            numFood--;
                            System.out.println(numFood);
                            if(numFood == 0){
                                //handle you win here
                                System.out.println("YOU WIN");
                                board[0][0] = 100;
                                paint.redraw(board);
                            }
                            //NO BREAK
                        default:
                            board[x][y] = empty;
                            board[x][y+1] = player;
                            y++;
                            paint.redraw(board);
                            break;
                        }
                }   break;
            case 2:
                if(y>0){
                    switch (board[x][y-1]) {
                        case ghost:
                            gameLoss();
                            break;
                        case wall:
                            //do nothing
                            break;
                        case food:
                            numFood--;
                            System.out.println(numFood);
                            if(numFood == 0){
                                //handle you win here
                                board[0][0] = 100;
                                paint.redraw(board);
                            }
                            //NO BREAK
                        default:
                            board[x][y] = empty;
                            board[x][y-1] = player;
                            y--;
                            paint.redraw(board);
                            break;
                        }
                }   break;
            case 3:
                if(x>0){
                     switch (board[x-1][y]) {
                        case ghost:
                            gameLoss();
                            break;
                        case wall:
                            //do nothing
                            break;
                        case food:
                            numFood--;
                            System.out.println(numFood);
                            if(numFood == 0){
                                //handle you win here
                                System.out.println("YOU WIN");
                                board[0][0] = 100;
                                paint.redraw(board);
                            }
                            //NO BREAK
                        default:
                            board[x][y] = empty;
                            board[x-1][y] = player;
                            x--;
                            paint.redraw(board);
                            break;
                        }
                }   break;
            default:
                if(x<bwidth-1){
                    switch (board[x+1][y]) {
                        case ghost:
                            gameLoss();
                            break;
                        case wall:
                            //do nothing
                            break;
                            case food:
                            numFood--;
                                System.out.println(numFood);
                            if(numFood == 0){
                                //handle you win here
                                System.out.println("YOU WIN");
                                board[0][0] = 100;
                                paint.redraw(board);
                            }
                            //NO BREAK
                        default:
                            board[x][y] = empty;
                            board[x+1][y] = player;
                            x++;
                            paint.redraw(board);
                            break;
                        }
                }   break;
        }
        aStarAlgo();
    }
    

    public static void main(String[] args) {

        PacmanCSC320 p = new PacmanCSC320();
        p.setUp();
        
        
    }
    
}
