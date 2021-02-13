import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RoomService } from 'src/app/services/room.service';
import { Room } from '../room';
@Component({
  selector: 'app-update-room',
  templateUrl: './update-room.component.html',
  styleUrls: ['./update-room.component.scss']
})
export class UpdateRoomComponent implements OnInit {
  id!: number;
  room!: Room;
  submitted = false;
  constructor( private route: ActivatedRoute, private router: Router, private roomService: RoomService
  ) { }
  ngOnInit(): void {
    this.room = new Room();
    this.id = this.route.snapshot.params['id'];
    this.roomService.getRoom(this.id).subscribe((data: any) => {
          console.log(data);
          this.room = data;
        }, (error: any) => console.log(error));
  }
  updateRoom() {
    this.roomService.updateRoom(this.id, this.room).subscribe(
        (data: any) => console.log(data),
        (error: any) => console.log(error)
      ) ;
    this.room = new Room();
    this.goToList();
  }
  onSubmit() {
    this.updateRoom();
  }
  goToList() {
    this.router.navigate(['/rooms']);
  }
}
