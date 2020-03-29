package com.recipe.cook;


import com.recipe.cook.entity.AutoCompleteResults;
import com.recipe.cook.entity.AutoCompletedItem;
import com.recipe.cook.entity.MenuItem;
import com.recipe.cook.entity.MenuItemResults;
import com.recipe.cook.service.MenuItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuItemServiceTests {


    @InjectMocks
    private MenuItemServiceImpl menuItemServiceMock;

    @Mock
    private RestTemplate restTemplateMock;


    @BeforeEach
    public void setUrl(){
        ReflectionTestUtils.setField(menuItemServiceMock, "spoonacularUrl", "https://api.spoonacular.com");
    }

    @Test
    public void searchMenuItems_Success(){
        MenuItemResults menuItemResultsSpy = spy(MenuItemResults.class);
        menuItemResultsSpy.setTotalMenuItems(1);

        when(restTemplateMock.getForObject(anyString(), eq(MenuItemResults.class))).thenReturn(menuItemResultsSpy);

        assertNotNull(menuItemServiceMock.searchMenuItems("Test", 1, 1, 1));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(MenuItemResults.class));
    }

    @Test
    public void searchMenuItems_fail(){
        MenuItemResults menuItemResultsSpy = spy(MenuItemResults.class);
        menuItemResultsSpy.setTotalMenuItems(0);

        when(restTemplateMock.getForObject(anyString(), eq(MenuItemResults.class))).thenReturn(menuItemResultsSpy);

        assertNull(menuItemServiceMock.searchMenuItems("Test", 1, 1, 1));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(MenuItemResults.class));
    }

    @Test
    public void searchMenuItemsAutoComplete_success(){
        AutoCompleteResults autoCompleteResultsSpy = spy(AutoCompleteResults.class);
        List<AutoCompletedItem> menuItemList = new ArrayList<>();
        menuItemList.add(new AutoCompletedItem());
        autoCompleteResultsSpy.setResults(menuItemList);

        when(restTemplateMock.getForObject(anyString(), eq(AutoCompleteResults.class))).thenReturn(autoCompleteResultsSpy);

        assertNotNull(menuItemServiceMock.searchMenuItemsAutoComplete(anyString(), anyInt()));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(AutoCompleteResults.class));
    }

    @Test
    public void searchMenuItemsAutoComplete_fail(){
        AutoCompleteResults autoCompleteResultsSpy = spy(AutoCompleteResults.class);
        List<AutoCompletedItem> menuItemList = new ArrayList<>();
        autoCompleteResultsSpy.setResults(menuItemList);

        when(restTemplateMock.getForObject(anyString(), eq(AutoCompleteResults.class))).thenReturn(autoCompleteResultsSpy);

        assertNull(menuItemServiceMock.searchMenuItemsAutoComplete(anyString(), anyInt()));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(AutoCompleteResults.class));
    }

    @Test
    public void getMenuItemInfo_success(){
        MenuItem menuItemMock = mock(MenuItem.class);

        when(restTemplateMock.getForObject(anyString(), eq(MenuItem.class))).thenReturn(menuItemMock);

        assertNotNull(menuItemServiceMock.getMenuItemInfo(4444));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(MenuItem.class));

    }


}



