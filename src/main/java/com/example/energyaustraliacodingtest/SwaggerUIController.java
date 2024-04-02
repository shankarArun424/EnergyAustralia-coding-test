package com.example.energyaustraliacodingtest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerUIController {
//    @Value("${springdoc.swagger-ui.path:#{T(org.)}}")
    @GetMapping("/swagger-ui/")
    public String swaggerUi() {
        return "redirect:/swagger-ui/index.html";
    }
}