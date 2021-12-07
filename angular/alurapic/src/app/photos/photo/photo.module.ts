import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PhotoComponent } from './photo.component';
import { PhotoService } from './photo.service';

@NgModule({
	declarations: [
		PhotoComponent,
	],
	imports: [
		CommonModule,
	],
	exports: [
		PhotoComponent,
	],
	providers: [
		PhotoService,
	]
})
export class PhotoModule { }
