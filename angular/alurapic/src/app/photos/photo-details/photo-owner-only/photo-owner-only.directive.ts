import { Directive, ElementRef, OnInit, Renderer2 } from "@angular/core";
import { Photo } from "../../photo/photo";
import { Input } from "@angular/core";
import { UserService } from "src/app/core/user/user.service";

@Directive({
    selector :'[photoOwnerOnly]'
})
export class PhotoOwnerOnlyDirective implements OnInit {

    @Input() ownedPhoto: Photo;

    constructor(
        private element: ElementRef<any>,
        private renderer: Renderer2,
        private userService: UserService) {}

    ngOnInit(): void {
        this.userService.getUserLogged().subscribe(user => {
            if (!user || user.userId !== this.ownedPhoto.userId) {
                this.renderer.setStyle(this.element.nativeElement, 'display', 'none');
            }
        })
    }
}