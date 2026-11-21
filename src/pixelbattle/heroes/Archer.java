package pixelbattle.heroes;

import pixelbattle.core.Hero;
import pixelbattle.core.Skill;
import pixelbattle.core.Target;
import pixelbattle.engine.Sprite;
import pixelbattle.core.Fighter;
import pixelbattle.core.Ultimate;

/** Лучниця — швидка й спритна. */
public class Archer extends Hero {

    public Archer() {
        super("Лучниця", 80, new Sprite("elf_f_idle_anim", 4, 4));

        addSkill(new Skill("Постріл", Target.ENEMY, 0, (user, target) ->
                user.getName() + " стріляє в " + target.getName() + ": "
                        + target.takeDamage(random(10, 16)) + " шкоди"));

        // TODO 3: вміння «Подвійний постріл» (Target.ENEMY, перезарядка 1) ЛЯМБДОЮ:
        //   два постріли по random(7, 11); у журнал — сума шкоди.
        addSkill(new Skill("Подвійний постріл", Target.ENEMY, 1, (user, target) -> {
            int first = target.takeDamage(random(7, 11));
            int second = target.takeDamage(random(7, 11));
            int total = first + second;
            return user.getName() + " двічі стріляє в " + target.getName() + ": " + total + " шкоди";
        }));

        // TODO 9: ультимейт «Злива стріл» — АНОНІМНИЙ КЛАС з полем-лічильником:
        //   кожен живий ворог отримує N стріл по random(4, 8) шкоди,
        //   де N = 3 при першому використанні, 4 — при другому, 5 — при третьому...
        //   (лічильник — поле всередині анонімного класу; знадобиться вкладений цикл).
        setUltimate(new Ultimate() {
            private int arrowCount = 3;

            @Override
            public String getName() {
                return "Злива стріл";
            }

            @Override
            public String getDescription() {
                return "Кілька стріл по 4–8 шкоди всім живим ворогам";
            }

            @Override
            public String activate(Fighter user, Fighter[] allies, Fighter[] enemies) {
                int totalDamage = 0;

                for (Fighter enemy : enemies) {
                    if (enemy.isAlive()) {
                        for (int i = 0; i < arrowCount && enemy.isAlive(); i++) {
                            totalDamage += enemy.takeDamage(random(4, 8));
                        }
                    }
                }
                int usedArrows = arrowCount;
                arrowCount++;
                return user.getName() + " використовує Зливу стріл! " + usedArrows + "стріли на ворога. Загальна шкода: "
                        + totalDamage;


            }
        });
    }

    // TODO 5: пасивка «Ухилення» — перевизначте modifyIncomingDamage:
    //   з імовірністю 25% (chance(25)) шкода дорівнює 0, інакше — без змін.
    @Override
    protected int modifyIncomingDamage(int damage) {
        if (chance(25)) {
            return 0;
        }
        return damage;
    }

}
