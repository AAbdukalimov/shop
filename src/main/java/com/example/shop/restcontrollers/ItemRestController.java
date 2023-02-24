package com.example.shop.restcontrollers;

import com.example.shop.dto.ItemDto;
import com.example.shop.entities.Item;
import com.example.shop.service.item.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Item REST controller", description = "Item API")
@RequestMapping("/rest/item")
@RestController
@AllArgsConstructor
public class ItemRestController {

    private ItemService itemService;

    @PostMapping("/create")
    @Operation(summary = "Create item")
    @ResponseStatus(HttpStatus.CREATED)
    public Item create(@Valid @RequestBody ItemDto itemDto) {
        return itemService.create(itemService.toItem(itemDto));
    }

    @GetMapping()
    @Operation(summary = "Find all items")
    @ResponseStatus(HttpStatus.OK)
    public List<Item> findAll() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find item by id")
    @ResponseStatus(HttpStatus.OK)
    public Item findById(@PathVariable(value = "id") Long id) {
        return itemService.findById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete item by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) {
        itemService.deleteById(id);
    }


}
