package com.example.docker_render;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductoController {

    private final ProductoRepository productoRepo;
    private final CategoriaRepository categoriaRepo;

    public ProductoController(ProductoRepository productoRepo, CategoriaRepository categoriaRepo) {
        this.productoRepo = productoRepo;
        this.categoriaRepo = categoriaRepo;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("productos", productoRepo.findAll());
        model.addAttribute("categorias", categoriaRepo.findAll());
        return "index";
    }

    @PostMapping("/producto")
    public String guardarProducto(
            @RequestParam String nombre,
            @RequestParam double precio,
            @RequestParam Long categoriaId) {

        Categoria cat = categoriaRepo.findById(categoriaId).orElse(null);

        Producto p = new Producto();
        p.setNombre(nombre);
        p.setPrecio(precio);
        p.setCategoria(cat);

        productoRepo.save(p);

        return "redirect:/";
    }
}

