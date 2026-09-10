package com.panzer.mods.dice_and_delish.perf;

/**
 * Bitfield layout (LSB -> MSB, 32 bits total) for the Skillet "egg cooking alone"
 * sub-state (previously two separate {@code int} fields: {@code eggAloneProgress} and
 * {@code eggAloneCookTime}).
 *
 * <p>{@code eggAloneCookTime} in practice only ever takes two values at runtime
 * ({@code 0} = not cooking, or {@code CookRecipe.DEFAULT_COOKING_TIME} = cooking), so it is
 * represented here as a single ACTIVE flag bit rather than a full duration field. The actual
 * duration constant lives in {@code CookRecipe.DEFAULT_COOKING_TIME} and is looked up by the
 * caller when needed; packing an already-constant value would just be a masked copy of a
 * compile-time constant.
 *
 * <table border="1" cellpadding="5" cellspacing="0">
 *   <thead>
 *     <tr>
 *       <th align="center">Bits</th>
 *       <th align="left">Function</th>
 *       <th align="left">Description</th>
 *     </tr>
 *   </thead>
 *   <tbody>
 *     <tr>
 *       <td align="center"><b>0</b></td>
 *       <td><code>ACTIVE</code></td>
 *       <td><code>1</code> = egg is cooking alone (equivalent to {@code eggAloneCookTime > 0}).</td>
 *     </tr>
 *     <tr>
 *       <td align="center"><b>1-9</b></td>
 *       <td><code>PROGRESS</code></td>
 *       <td>9 bits (0-511). Mirrors the {@code PROGRESS} field width used by {@link StateMask}.</td>
 *     </tr>
 *     <tr>
 *       <td align="center"><b>10-31</b></td>
 *       <td><code>UNUSED</code></td>
 *       <td>Reserved.</td>
 *     </tr>
 *   </tbody>
 * </table>
 */
public final class EggStateMask {

    public static final int ACTIVE_BIT = 0;
    public static final int PROGRESS_SHIFT = 1;

    public static final int PROGRESS_BITS = 9;

    public static final int ACTIVE_MASK = 1 << ACTIVE_BIT;
    public static final int PROGRESS_MASK = ((1 << PROGRESS_BITS) - 1) << PROGRESS_SHIFT;

    public static final int PROGRESS_MAX = (1 << PROGRESS_BITS) - 1;

    private EggStateMask() {
    }

    public static boolean isActive(int packed) {
        return (packed & ACTIVE_MASK) != 0;
    }

    public static int setActive(int packed, boolean value) {
        int v = (value ? 1 : 0) << ACTIVE_BIT;
        return (packed & ~ACTIVE_MASK) | v;
    }

    public static int getProgress(int packed) {
        return (packed & PROGRESS_MASK) >>> PROGRESS_SHIFT;
    }

    public static int setProgress(int packed, int progress) {
        int clamped = progress & PROGRESS_MAX;
        return (packed & ~PROGRESS_MASK) | (clamped << PROGRESS_SHIFT);
    }

    public static int incrementProgress(int packed) {
        int next = (getProgress(packed) + 1) & PROGRESS_MAX;
        return (packed & ~PROGRESS_MASK) | (next << PROGRESS_SHIFT);
    }

    /**
     * Builds a packed state with progress reset to 0 and the ACTIVE flag set as requested.
     * Equivalent to the old pattern {@code eggAloneProgress = 0; eggAloneCookTime = active ? DEFAULT : 0;}
     */
    public static int startOrClear(boolean active) {
        return setActive(0, active);
    }
}
