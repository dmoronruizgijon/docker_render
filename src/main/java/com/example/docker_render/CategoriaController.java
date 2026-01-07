package com.example.docker_render;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;


    public CategoriaController(CategoriaRepository categoriaRepository,
                           ProductoRepository productoRepository) {
    this.categoriaRepository = categoriaRepository;
    this.productoRepository = productoRepository;
}


    // LISTAR
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "categorias/index";
    }

    // FORM CREAR
    @GetMapping("/nueva")
    public String formularioNueva(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/form";
    }

    // GUARDAR (crear o editar)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Categoria categoria) {
        categoriaRepository.save(categoria);
        return "redirect:/categorias";
    }

    // FORM EDITAR
    @GetMapping("/editar")
    public String editar(@RequestParam Long id, Model model) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow();
        model.addAttribute("categoria", categoria);
        return "categorias/form";
    }

    // BORRAR
    @GetMapping("/eliminar")
    public String eliminar(@RequestParam Long id, RedirectAttributes redirect) {

        if (!productoRepository.findByCategoriaId(id).isEmpty()) {
            redirect.addFlashAttribute("error", "No se puede borrar la categoría porque tiene productos");
            return "redirect:/categorias";
        }

        categoriaRepository.deleteById(id);
        return "redirect:/categorias";
    }
}
