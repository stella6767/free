package com.stella.free.view.controller

import com.stella.free.util.logger
import com.stella.free.view.page.layout.LayoutViewComponent
import com.stella.free.view.page.resume.ResumeViewComponent

import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ResumeController(
    private val layoutViewComponent: LayoutViewComponent,
    private val resumeViewComponent: ResumeViewComponent,
) {

    private val log = logger()

    @GetMapping("/resume")
    fun resume(): ViewContext {

        return layoutViewComponent.render(resumeViewComponent.render())
    }




}