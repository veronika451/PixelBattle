package pixelbattle.enemies;

import pixelbattle.core.Enemy;
import pixelbattle.core.Skill;
import pixelbattle.core.Target;
import pixelbattle.engine.Sprite;
import pixelbattle.core.Fighter;
import pixelbattle.core.Ultimate;

/** Огр — бос. Повільний, але дуже сильний. */
public class Ogre extends Enemy {

    public Ogre() {
        super("Огр", 200, new Sprite("ogre_idle_anim", 4, 4));

        addSkill(new Skill("Удар кийком", Target.ENEMY, 0, (user, target) -> {
            int dmg = random(14, 20);
            if (user.getHp() < user.getMaxHp() / 2) dmg += 8;    // лють
            return user.getName() + " б'є кийком " + target.getName() + ": "
                    + target.takeDamage(dmg) + " шкоди";
        }));

        // TODO 10: ультимейт «Землетрус» — АНОНІМНИЙ КЛАС:
        //   кожному живому герою 12 шкоди, а сам огр лікується на 20.
        //   Увага: для огра вороги — це герої, тобто масив enemies у activate(...).
        //   Огр використає ультимейт сам, щойно енергія заповниться.
        setUltimate(new Ultimate() {
            @Override
            public String getName(){
                return "Землетрус";
            }
            @Override
            public String getDescription(){
                return " 12 шкоди всім героям та огру 20 хп";
            }
            @Override
            public String activate( Fighter user, Fighter[] allies, Fighter[] enemies){
                int totalDamage = 0;
                for (Fighter enemy : enemies){
                    if (enemy.isAlive()){
                        totalDamage += enemy.takeDamage(12);
                    }
                }
                int healed = user.heal(20);
                return user.getName() + " використовує Землетрус! Шкода: " + totalDamage + ", відновлено HP: " + healed;
            }
        });
    }

    // ПРИКЛАД ПАСИВКИ: «Товста шкіра» — за один удар огр втрачає не більше 25 HP.
    @Override
    protected int modifyIncomingDamage(int damage) {
        return Math.min(damage, 25);
    }
}
