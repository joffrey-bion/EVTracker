package com.jbion.web.evstracker.model;

import com.jbion.web.evstracker.entities.Pokemon;
import com.jbion.web.evstracker.entities.Stats;

public class Calculator {

    private static final int MIN_IV = 0;
    private static final int MAX_IV = 31;
    private static final int MIN_EV = 0;
    private static final int MAX_EV = 255;

    public static void calculateIVs(Pokemon pokemon, Stats evs) {
        final Stats minIvs = pokemon.getMinIvs();
        final Stats maxIvs = pokemon.getMaxIvs();
        final Nature nature = pokemon.getNature();
        final int level = pokemon.getLastCheckpoint().getLevel();
        final Stats baseStats = pokemon.getSpecies().getBaseStats();
        final Stats currStats = pokemon.getLastCheckpoint().getStats();
        for (final Stat stat : Stat.values()) {
            final int[] iv = getIV(baseStats.get(stat), currStats.get(stat), evs.get(stat), level, stat, nature);
            minIvs.put(stat, iv[0]);
            maxIvs.put(stat, iv[1]);
        }
    }

    public static Stats[] calculateEVs(Pokemon pokemon) {
        final Stats minIvs = pokemon.getMinIvs();
        final Stats maxIvs = pokemon.getMaxIvs();
        final Nature nature = pokemon.getNature();
        final int level = pokemon.getLastCheckpoint().getLevel();
        final Stats baseStats = pokemon.getSpecies().getBaseStats();
        final Stats currStats = pokemon.getLastCheckpoint().getStats();
        final Stats minEvs = new Stats();
        final Stats maxEvs = new Stats();
        for (final Stat stat : Stat.values()) {
            final int[] evMin = getEV(baseStats.get(stat), currStats.get(stat), maxIvs.get(stat), level, stat, nature);
            final int[] evMax = getEV(baseStats.get(stat), currStats.get(stat), minIvs.get(stat), level, stat, nature);
            System.out.println("ev min [0]=" + evMin[0] + " [1]=" + evMin[1]);
            System.out.println("ev max [0]=" + evMax[0] + " [1]=" + evMax[1]);
            minEvs.put(stat, evMin[0]);
            maxEvs.put(stat, evMax[1]);
        }
        return new Stats[] {minEvs, maxEvs};
    }

    public static void calculateStats(Pokemon pokemon) {
        final Nature nature = pokemon.getNature();
        final int level = pokemon.getLastCheckpoint().getLevel();
        final Stats baseStats = pokemon.getSpecies().getBaseStats();
        final Stats currStats = pokemon.getLastCheckpoint().getStats();
        // convention min IVs when they are known
        final Stats ivs = pokemon.getMinIvs();
        for (final Stat stat : Stat.values()) {
            final int base = baseStats.get(stat);
            final int iv = ivs.get(stat);
            final int ivsCorrected = iv + 2 * base + (stat == Stat.HP ? 100 : 0);
            final int preNatureCorrected = ivsCorrected * level / 100 + (stat == Stat.HP ? 10 : 5);
            final double natureCorrected = preNatureCorrected * nature.getCoefficient(stat);
            currStats.put(stat, (int) natureCorrected);
        }
    }

    private static int[] getIV(int base, int current, int ev, int level, Stat stat, Nature nature) {
        final int[] res = new int[2];
        int iv = MIN_IV;
        while (calculateStat(base, iv, ev, level, stat, nature) != current) {
            iv++;
        }
        res[0] = iv;
        while (iv <= MAX_IV && calculateStat(base, iv, ev, level, stat, nature) == current) {
            iv++;
        }
        res[1] = iv - 1;
        return res;
    }

    private static int[] getEV(int base, int current, int iv, int level, Stat stat, Nature nature) {
        final int[] res = new int[2];
        int ev = MIN_EV;
        while (calculateStat(base, iv, ev, level, stat, nature) != current) {
            ev++;
        }
        res[0] = ev;
        while (ev <= MAX_EV && calculateStat(base, iv, ev, level, stat, nature) == current) {
            ev++;
        }
        res[1] = ev - 1;
        return res;
    }

    private static int calculateStat(int base, int iv, int ev, int level, Stat stat, Nature nature) {
        final int ivsCorrected = iv + ev / 4 + 2 * base + (stat == Stat.HP ? 100 : 0);
        final int preNatureCorrected = ivsCorrected * level / 100 + (stat == Stat.HP ? 10 : 5);
        final double natureCorrected = preNatureCorrected * nature.getCoefficient(stat);
        return (int) natureCorrected;
    }
}
