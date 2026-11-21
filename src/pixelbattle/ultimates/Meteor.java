package pixelbattle.ultimates;
import pixelbattle.core.Fighter;
import pixelbattle.core.Ultimate;


 // TODO 8: ультимейт «Метеорит» — ЗВИЧАЙНИЙ (іменований) клас:

  public class Meteor implements Ultimate {
     @Override
      public String getName(){
         return "Метеорит";
     }
     @Override
      public String getDescription() {
         return " завдає шкоди кожному живому ворогу";
     }
     @Override
      public String activate(Fighter user, Fighter[] allies, Fighter[] enemies){
         int totalDamage = 0;
         for (Fighter enemy : enemies) {
             if (enemy.isAlive()) {
                 int damage = enemy.takeDamage(Fighter.random(20, 30));
                 totalDamage += damage;
             }
         }
         return user.getName() + " викликає Метеорит! Загальна шкода: " + totalDamage;
         }
      }

