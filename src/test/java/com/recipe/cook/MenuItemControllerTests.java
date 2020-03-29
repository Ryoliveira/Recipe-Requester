package com.recipe.cook;


import com.recipe.cook.controller.MenuItemController;
import com.recipe.cook.entity.AutoCompleteResults;
import com.recipe.cook.entity.MenuItem;
import com.recipe.cook.entity.MenuItemResults;
import com.recipe.cook.service.MenuItemServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class MenuItemControllerTests {

    MockMvc menuItemControllerMock;

    @Mock
    MenuItemServiceImpl menuItemServiceMock;

    @Before
    public void setUp(){
        MenuItemController menuItemController = new MenuItemController(menuItemServiceMock);
        menuItemControllerMock = MockMvcBuilders.standaloneSetup(menuItemController).build();
    }

    @Test
    public void getSearchMenuItemPage_success() throws Exception {
        menuItemControllerMock.perform(get("/menu/menu-item/search"))
                              .andExpect(status().isOk())
                              .andExpect(model().attribute("menuItemSearch", true))
                              .andExpect(view().name("menu/menu-home"))
                              .andDo(MockMvcResultHandlers.print())
                              .andReturn();
    }

    @Test
    public void getMenuItemSearchResults_success() throws Exception {
        MenuItemResults menuItemResultsMock = mock(MenuItemResults.class);
        when(menuItemServiceMock.searchMenuItems(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(menuItemResultsMock);


        menuItemControllerMock.perform(get("/menu/menu-item/results")
                .param("query", anyString())
                .param("minCalories", String.valueOf(anyInt()))
                .param("maxCalories", String.valueOf(anyInt()))
                .param("number", String.valueOf(anyInt())))
                .andExpect(status().isOk())
                .andExpect(model().attribute("menuItemResults", menuItemResultsMock))
                .andExpect(view().name("menu/menu-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void getMenuItemSearchResults_redirect() throws Exception {
        when(menuItemServiceMock.searchMenuItems(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(null);


        menuItemControllerMock.perform(get("/menu/menu-item/results")
                .param("query", anyString())
                .param("minCalories", String.valueOf(anyInt()))
                .param("maxCalories", String.valueOf(anyInt()))
                .param("number", String.valueOf(anyInt())))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("menuItemsNotFound", "No items found for current request"))
                .andExpect(view().name("redirect:/menu/menu-item/search"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void getAutoCompleteSearchMenuItemPage_success() throws Exception {
        menuItemControllerMock.perform(get("/menu/auto-complete/search"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("autoCompleteSearch", true))
                .andExpect(view().name("menu/menu-home"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    public void getAutoCompleteSearchMenuItemResults_success() throws Exception {
        AutoCompleteResults autoCompleteResultsMock = mock(AutoCompleteResults.class);
        when(menuItemServiceMock.searchMenuItemsAutoComplete(anyString(), anyInt())).thenReturn(autoCompleteResultsMock);

        menuItemControllerMock.perform(get("/menu/auto-complete/results")
                .param("query", anyString())
                .param("number", String.valueOf(anyInt())))
                .andExpect(status().isOk())
                .andExpect(model().attribute("AutoCompleteResults", autoCompleteResultsMock))
                .andExpect(view().name("menu/menu-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void getAutoCompleteSearchMenuItemResults_redirect() throws Exception {
        when(menuItemServiceMock.searchMenuItemsAutoComplete(anyString(), anyInt())).thenReturn(null);

        menuItemControllerMock.perform(get("/menu/auto-complete/results")
                .param("query", anyString())
                .param("number", String.valueOf(anyInt())))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("noMenuItemsFound", "No menu items found"))
                .andExpect(view().name("redirect:/menu/auto-complete/search"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();


    }

    @Test
    public void getMenuItemNutritionInfo_success() throws Exception {
        MenuItem menuItemMock = mock(MenuItem.class);
        when(menuItemServiceMock.getMenuItemInfo(anyInt())).thenReturn(menuItemMock);

        menuItemControllerMock.perform(get("/menu/menu-item/nutrition/{id}", anyInt()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("menuItemInfo", menuItemMock))
                .andExpect(view().name("menu/menu-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }






}
