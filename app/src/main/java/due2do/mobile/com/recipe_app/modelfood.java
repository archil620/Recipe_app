package due2do.mobile.com.recipe_app;

import java.io.Serializable;

/**
 * Created by Archil on 2018-04-06.
 */

public class modelfood implements Serializable {

    private int recipeimage;
    private String recipe_name, recipe_type, description, ingrediants,key;
    private String imageUri;

    public modelfood(){

    }

    public modelfood(int recipeimage, String recipe_name, String recipe_type, String description, String ingrediants) {
        this.recipeimage = recipeimage;
        this.recipe_name = recipe_name;
        this.recipe_type = recipe_type;
        this.description = description;
        this.ingrediants = ingrediants;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getRecipeimage() {
        return recipeimage;
    }

    public void setRecipeimage(int recipeimage) {
        this.recipeimage = recipeimage;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getRecipe_type() {
        return recipe_type;
    }

    public void setRecipe_type(String recipe_type) {
        this.recipe_type = recipe_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngrediants() {
        return ingrediants;
    }

    public void setIngrediants(String ingrediants) {
        this.ingrediants = ingrediants;
    }
}

