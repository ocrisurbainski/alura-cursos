import { Directive, ElementRef, Host, HostListener, Input, Renderer2 } from '@angular/core';

@Directive({
  selector: '[appDarkenOnHover]'
})
export class DarkenOnHoverDirective {

    @Input() brightness = '70%';

    constructor(
        private el: ElementRef,
        private render: Renderer2) { }

    @HostListener('mouseover')
    darkenOn():void {
        this.render.setStyle(this.el.nativeElement, 'filter', `brightness(${this.brightness})`);
    }

    @HostListener('mouseleave')
    darkenOff():void {
        this.render.setStyle(this.el.nativeElement, 'filter', 'brightness(100%)');
    }

}
