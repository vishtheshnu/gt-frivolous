# Botania Learnings
### Knowledge and gotchas of Botania mod integration

<hr>

The goal of botania integration was two-fold:
1. Create GT machines that run on Botania mana instead of EU
2. Create GT multiblocks that run on Botania mana instead of EU

Adding such a feature is normally easy;
you just use a mixin to inject the other mod's resource capability into your machines,
or add it via a new machine class. (or something, I haven't implemented suc ha feature at the time of writing)

However, Botania mana isn't a capability; it's an integer. _Just_ an integer.

So how do you implement a machine that consumes Botania mana? Here are my steps:

1. Learn how Botania does it. Functional flora that consume mana extend the following class:
[FunctionalFlowerBlockEntity](https://github.com/VazkiiMods/Botania/blob/1.20.x/Xplat/src/main/java/vazkii/botania/api/block_entity/FunctionalFlowerBlockEntity.java)
   - It contains a lot of extraneous features, so look at its super class,
   [BindableSpecialFlowerBlockEntity](https://github.com/VazkiiMods/Botania/blob/1.20.x/Xplat/src/main/java/vazkii/botania/api/block_entity/BindableSpecialFlowerBlockEntity.java#L43)
   - Follow the chain of classes it extends (and interfaces) to see its functionality
2. How Botania does it:
   - Current and max mana are represented as an integer and exposes public methods for reading these values.
   - The mana pool it's currently bound to is stored as a BlockPos (x,y,z coords), which is set when first placed.
   - WandBindable interface allows the Wand of the Forest to rebind it.
3. Make a machine that does all of the above
   - Botania's license is nice enough to allow copying code, and if you're making an addon that uses botania,
   there's less of an issue with it!
   - I copied the mana pool binding and Wand HUD code in the machine class.
   - Create custom RecipeHandler class that takes the machine as a constructor parameter and consumes mana from it in place of EU.
4. Gotchas
   - Wand of the Forest expects the BlockEntity to implement WandBindable, not a GT machine.
     - Create a mixin for GTCEu that has the MetaMachineBlockEntity class implement WandBindable
     - Have WandBindable's instantiated methods check if getMachine() instanceof WandBindable, and if so, 
     cast it and call its respective WandBindable method. Otherwise, return false/null/indicate failure.
   - Hook up the HUD
     - See `registerBotaniaWandHudCaps()` in FrivolousMachines.java, and how it's called in GTFrivolous.java
     - See `attachBeCapabilities()` and `clientSetup()` in GTFrivolous.java. Yes, all that lazy loading is necessary.
   - Trying to bind machines only rotates them!
     - Botania checks if a BlockEntity has a rotation blockstate and acts on that before checking if it's bindable.
     - Give your machines the block tag `botania:unwandable` for it to ignore rotation and perform binding instead.