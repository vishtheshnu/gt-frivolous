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
   - Note the limitation: this is a class, and your machines already extend some variant of the `WorkableMachine` class.
   We'll have to simply copy-paste the functionality into our new Machine class.
2. Understand the Mechanics:
   - Current and max mana are represented as an integer and exposes public methods for reading these values.
     - Maybe put these methods in an interface if you have multiple Machine classes, so the MachineEntity can check
     `instanceof` that interface instead of each specific class (eg. single block machine vs multiblock hatch)
   - The mana pool it's currently bound to is stored as a BlockPos (x,y,z coords), which is set when first placed.
   Several public methods are exposed to allow rebinding.
     - Consider adding this to the interface as well? With default implementations for any that only reference static objects.
   - WandBindable interface allows the Wand of the Forest to rebind it.
3. Make a machine that does all of the above
   - Botania's license is nice enough to allow copying code for projects like these, and if you're making an addon that
   uses botania and is free (no duh), that's even more legitimacy!
   - I copied the mana pool binding and Wand HUD code in the machine class, while making minor changes to have it work with the GT Machine class.
   - Create custom RecipeHandler class that takes the machine as a constructor parameter and consumes mana from it in place of EU.
4. Gotchas
   - Wand of the Forest expects the BlockEntity to implement WandBindable, not a GT machine.
     - Create a mixin for GTCEu that makes the MetaMachineBlockEntity class implement WandBindable
     - Have WandBindable's instantiated methods check `if(getMachine() instanceof WandBindable bindableMachine)`, and if so, 
     cast it and call its respective WandBindable method. Otherwise, return indicating failure (false, null, etc.).
   - Hook up the HUD
     - See `registerBotaniaWandHudCaps()` in FrivolousMachines.java, and how it's called in GTFrivolous.java
     - See `attachBeCapabilities()` and `clientSetup()` in GTFrivolous.java. Yes, all that lazy loading is necessary,
     otherwise calling the `MachineBlockEntity.getMachine()` will return null because it wouldn't have been loaded yet.
   - Trying to bind machines only rotates them!
     - Botania checks if a BlockEntity has a rotation blockstate and acts on that before checking if it's bindable.
     - Give your machines the block tag `botania:unwandable` for it to ignore rotation and perform binding instead.
     - I tried writing a mixin that added a check for my botanic/wand-bindable machines before the rotation check,
     and while it worked in dev, it crashed when I exported and put it in a modpack due to not being able to apply the mixin.
     It might be a niche issue due to how I have the project set up, but adding the block tag is much cleaner anyway.