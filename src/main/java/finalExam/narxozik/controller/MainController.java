package finalExam.narxozik.controller;

import finalExam.narxozik.model.Bonus;
import finalExam.narxozik.model.PC;
import finalExam.narxozik.model.Users;
import finalExam.narxozik.repository.BonusRepository;
import finalExam.narxozik.repository.PcRepository;
import finalExam.narxozik.services.impl.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private PcRepository pcRepository;

    @Autowired
    private BonusRepository bonusRepository;


    @Autowired
    private UserServices userServices;

    @GetMapping(value = "/")
    public String indexPage(Model model){
        model.addAttribute("currentUser" ,getCurrentUser());
        List<Bonus> bonuses = bonusRepository.findAll();
        model.addAttribute("bonuses" ,bonuses);
        List<PC> pcs = pcRepository.findAll();
        model.addAttribute("pcs" , pcs);
        return "index";
    }

    @GetMapping(value = "/login")
    public String login(Model model){
        model.addAttribute("currentUser" ,getCurrentUser());
        return "login";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("currentUser" ,getCurrentUser());
        return "profile";
    }

    @GetMapping(value = "/adminpanel")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String admin(Model model){
        model.addAttribute("currentUser" ,getCurrentUser());
        return "adminpanel";
    }

    @GetMapping(value = "/moderatorpanel")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public String moderator(Model model){
        List<Bonus> bonuses = bonusRepository.findAll();
        model.addAttribute("bonuses" ,bonuses);
        model.addAttribute("currentUser" ,getCurrentUser());
        return "moderatorpanel";
    }

    @GetMapping(value = "/bonuses")
    public String bonus_page(Model model){
        List<Bonus> bonuses = bonusRepository.findAll();
        model.addAttribute("bonuses" ,bonuses);
        model.addAttribute("currentUser" ,getCurrentUser());
        return "bonuses";
    }


    @GetMapping(value = "/403")
    public String accessDeniePage(Model model){
        model.addAttribute("currentUser" ,getCurrentUser());
        return "403";
    }




    @PostMapping(value = "/addPc")
    public String addPc(@RequestParam(name = "pc_name")  String name,
                        @RequestParam(name = "pc_image") String image ,
                        @RequestParam(name = "pc_proc") String proc,
                        @RequestParam(name = "pc_video") String video,
                        @RequestParam(name = "pc_ozu") String ozu ,
                        @RequestParam(name = "pc_memory") String memory,
                        @RequestParam(name = "pc_price") double price,
                        @RequestParam(name = "bonus_id") Long bonus_id){

        Bonus bonus = bonusRepository.findById(bonus_id).orElse(null);

        if(bonus_id!=null){

        PC pc = new PC();
        pc.setName(name);
        pc.setDescription(image);
        pc.setProc(proc);
        pc.setVideo(video);
        pc.setOzu(ozu);
        pc.setMemory(memory);
        pc.setPrice(price);
        pc.setBonus(bonus);

        pcRepository.save(pc);

        }
        return "redirect:/";
    }


    @PostMapping(value = "/addBonus")
    public String addBonus(@RequestParam(name = "bonus_name")  String name,
                        @RequestParam(name = "bonus_img") String image ,
                        @RequestParam(name = "bonus_decr") String decr){



            Bonus bonus = new Bonus();
            bonus.setName(name);
            bonus.setPic(name);
            bonus.setDescription(decr);

            bonusRepository.save(bonus);

        return "redirect:/";
    }




    @GetMapping(value = "/details/{id}")
    public String details(@PathVariable(name = "id") Long id , Model model){
        PC pc = pcRepository.findById(id).orElse(null);
        model.addAttribute("pc" , pc );
        model.addAttribute("currentUser" ,getCurrentUser());
        List<Bonus> bonuses = bonusRepository.findAll();
        model.addAttribute("bonuses" ,bonuses);

        return "details";

    }


    @PostMapping(value = "/savePc")
    public String savePc(@RequestParam(name = "pc_id")  Long id,
                        @RequestParam(name = "pc_name")  String name,
                        @RequestParam(name = "pc_image") String image ,
                        @RequestParam(name = "pc_proc") String proc,
                        @RequestParam(name = "pc_video") String video,
                        @RequestParam(name = "pc_ozu") String ozu ,
                        @RequestParam(name = "pc_memory") String memory,
                        @RequestParam(name = "pc_price") double price ,
                         @RequestParam(name = "bonus_id") Long bonus_id){

         PC pc = pcRepository.findById(id).orElse(null);
        Bonus bonus = bonusRepository.findById(bonus_id).orElse(null);

         if(pc!=null && bonus_id!=null){
             pc.setName(name);
             pc.setDescription(image);
             pc.setProc(proc);
             pc.setVideo(video);
             pc.setOzu(ozu);
             pc.setMemory(memory);
             pc.setPrice(price);
             pc.setBonus(bonus);
             pcRepository.save(pc);
             return "redirect:/details/"+id;
         }
        return "redirect:/";
    }



    @PostMapping(value = "/deletePc")
    public String deletePc(@RequestParam(name = "pc_id")  Long id  ){

        PC pc = pcRepository.findById(id).orElse(null);

        if(pc!=null){
            pcRepository.delete(pc);
        }
        return "redirect:/";

    }

    @PostMapping(value = "/deleteBonus")
    public String deleteBonus(@RequestParam(name = "bb_id")  Long id  ){

        Bonus bonus = bonusRepository.findById(id).orElse(null);

        if(bonus!=null){
            bonusRepository.delete(bonus);
        }
        return "redirect:/";

    }



    @GetMapping(value = "/register")
    public String register(Model model){
        model.addAttribute("currentUser",getCurrentUser());
        return "register";
    }

    @PostMapping(value = "/register")
    public String toRegister(@RequestParam(name = "user_email") String email,
                             @RequestParam(name = "user_password") String password,
                             @RequestParam(name = "user_rePassword") String rePassword,
                             @RequestParam(name = "user_fullName") String fullName){

        if (password.equals(rePassword)){
            Users newUser = new Users();
            newUser.setPassword(password);
            newUser.setFull_name(fullName);
            newUser.setEmail(email);

            if (userServices.createUser(newUser)!=null){
                return "redirect:/register?success";
            }
        }

        return "redirect:/register?error";
    }





    private Users getCurrentUser(){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         if(!(authentication instanceof AnonymousAuthenticationToken)){
             Users currentUser = (Users) authentication.getPrincipal();
             return currentUser;
         }
         return null;
    }




}
