package uvinfo.bomberman;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class Monstre extends Personnage {

	long tempsDebut = System.currentTimeMillis();
	
	float difficult = 1;
	
	
	public Monstre() {
	}	


	public void initAnimation() throws SlickException{
		CreateAnimation("sprites/monstre.png",96,48,3);		
		posX(100);
		posY(100);
		SetDirection(2);
	}
	
	public void Move(Avatar victime)
	{				
		int LargeurHauteurCollision = 50;
		
		if(Math.abs(this.posX()-victime.posX()) > LargeurHauteurCollision || Math.abs(this.posY()-victime.posY()) > LargeurHauteurCollision)
		{			
			
			long tempsFin = System.currentTimeMillis();//temps actuel
			
			float duree = (tempsFin - tempsDebut) / 1000F;//calcul de la durée en seconde
			
			if(duree > RandTime())//si la durée du temps aleatoire est depassée
			{				
				ChangeDirection();			
			}
			
			Avance();
		}	

		else 
		{
			victime.Hurted(1);
		}
	}		
	
	public void OpposeDirection()
	{
		switch(GetDirection())
		{
			case 0:SetDirection(2);break;
			case 1:SetDirection(3);break;
			case 2:SetDirection(0);break;
			case 3:SetDirection(1);break;
		}
		
		for(int i = 0; i<3;i++) Avance();
	}
	
	public void ChangeDirection()
	{
		Random rand = new Random();
		int random = rand.nextInt((4 - 0) + 1) + 0;// de 0 à 3
		
		while(random == GetDirection()) random = rand.nextInt((4 - 0) + 1) + 0;// de 0 à 3
		
		SetDirection(random);//on change de direction aléatoirement
	}
	
	private float RandTime()
	{
		int minRand = 0;
		int maxRand = 50;
		
		Random randT = new Random();
		float rand = randT.nextInt((maxRand - minRand) + 1) + minRand;;//rand de 0 a 51 exclus
		rand /= 10;//divion par 10 pour obtenir un float de maximum 3
		return rand;// de 0 à 3 avec un pas de 0.1
	}
	
	private void Avance()
	{
		tempsDebut = System.currentTimeMillis();//commence à compter le temps au moment du deplacement
						
		switch (GetDirection()) {

	    case 0: moveDown(); break;
	    case 1: moveLeft(); break;
	    case 2: moveUp(); break;
	    case 3: moveRight(); break;
	    }		
		
		this.SetMoving(false);
	}
	
	
	public void render()
	{
		if(this.getHasBeenHurted()){
			GetAnimation(GetDirection()+(isMoving() ? 4 : 0)).draw(this.posX()-40, this.posY()-50, new Color(255,0,0, 50));
		}else{
			GetAnimation(GetDirection()+(isMoving() ? 4 : 0)).draw(this.posX()-40, this.posY()-50);
		}
	}	
	
	public void update(Avatar avatar, GameContainer container){
		/*avatar.getBomb().update(this, 0);
		avatar.getSuperBomb().update(this, 0);*/
		// gagné si monstre est mort
		if(!this.IsAlive())
		{
			javax.swing.JOptionPane.showMessageDialog(null,"You Win"); 
			container.exit();
		}
	}
}
