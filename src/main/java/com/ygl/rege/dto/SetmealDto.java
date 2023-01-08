package com.ygl.rege.dto;

import com.ygl.rege.entity.Setmeal;
import com.ygl.rege.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
