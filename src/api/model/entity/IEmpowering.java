package api.model.entity;

import model.entity.Modifier;

import java.util.Random;

/**
 * An interface used by entities that can apply modifiers to other entities
 * @author Mike Garay
 */
public interface IEmpowering {

     /**
      * Obtains the modifier stored in an instance of the implementing class
      * @return The modifier instance stored in this instance
      */
     Modifier getModifier();

     /**
      * Sets the modifier to store in an instance of the implementing class
      * @param modifier The modifier instance to store
      */
     void setModifier(Modifier modifier);

     /**
      * Determines if the instance of the implementing class has applied its modifier
      * This is used as a basis for any post-modifier-application logic
      * @return Whether or not the instance of the implementing class has applied its modifier
      */
     boolean hasAppliedModifier();

     /**
      * Sets the stored boolean in the instance of the implementing class that
      * determines if it has applied the stored modifier to another entity
      * @param hasAppliedModifier The value to set the stored boolean to
      */
     void setHasAppliedModifier(boolean hasAppliedModifier);

     /**
      * Sets a random modifier for an instance of the implementing class
      * @param value the value to instantiate the Modifier instance with
      * @param duration The duration to instantiate the Modifier instance with
      */
     default void setRandomModifier(double value, int duration){
          Random random = new Random();
          Modifier.ModifierType[] modifierTypes = Modifier.ModifierType.values();
          Modifier.ModifierType randomType = modifierTypes[random.nextInt(modifierTypes.length)];
          Modifier randomModifier = new Modifier(randomType, value, duration);
          this.setModifier(randomModifier);
     }
}
