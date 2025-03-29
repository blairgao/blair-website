package com.blairgao

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class LotteryController {
    private val prizes = listOf(
        Prize("$1,000,000", "🎉 Congratulations! You've won the grand prize! 🎉"),
        Prize("$100,000", "🎉 Amazing! You've won a major prize! 🎉"),
        Prize("$10,000", "🎉 Great job! You've won a prize! 🎉"),
        Prize("$1,000", "🎉 Nice! You've won a small prize! 🎉"),
        Prize("$100", "🎉 You've won a consolation prize! 🎉"),
        Prize("$0", "😢 Better luck next time!")
    )

    @GetMapping("/")
    fun home(model: Model): String {
        model.addAttribute("prize", prizes.random())
        return "index"
    }

    @GetMapping("/new_prize")
    @ResponseBody
    fun newPrize(): Prize {
        return prizes.random()
    }
} 