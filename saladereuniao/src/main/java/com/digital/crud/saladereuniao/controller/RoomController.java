package com.digital.crud.saladereuniao.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digital.crud.saladereuniao.exception.ResourceNotFoundException;
import com.digital.crud.saladereuniao.model.Room;
import com.digital.crud.saladereuniao.repository.RoomRepository;

/* Informar que é um RestController */
@RestController 
/* Habilitar o CrossOrigin para o front poder consumir
 * Pode habilidar de qualquer lugar, ou no caso, informar
 * o endpoint no qual a aplicação angular irá subir.
 * */
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class RoomController {

	@Autowired
	private RoomRepository roomRepository;

	@GetMapping("/rooms")
	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}

	@GetMapping("/rooms/{id}")
	public ResponseEntity<Room> getRoomById(@PathVariable(value = "id") long roomId)
			throws ResourceNotFoundException {

		Room room = roomRepository
				.findById(roomId)
				.orElseThrow(() -> new ResourceNotFoundException("Room not found for id:: " + roomId))
		;

		return ResponseEntity.ok().body(room);
	}
	
	/* @Valid -> Validar se o json que mandamos pra criarn a sala é válido  */
	@PostMapping("/rooms")
	public Room createRoom(@Valid @RequestBody Room room) {
		return roomRepository.save(room);
	}
	
	@PutMapping("/rooms/{id}")
	public ResponseEntity<Room> updateRoom(
			@PathVariable (value = "id") Long roomId,
			@Valid @RequestBody Room roomDetails
			) throws ResourceNotFoundException {
		
		Room room = roomRepository
				.findById(roomId)
				.orElseThrow(() -> new ResourceNotFoundException("Room not found for this id: " + roomId))
		;

		room.setName(roomDetails.getName());
		room.setDate(roomDetails.getDate());
		room.setStartHour(roomDetails.getStartHour());
		room.setEndHour(roomDetails.getEndHour());
		
		final Room updateRoom = roomRepository.save(room);
		
		return ResponseEntity.ok(updateRoom);
	}
	
	@DeleteMapping("/rooms/{id}")
	public Map<String, Boolean> deleteRoom(
			@PathVariable (value = "id") Long roomId
			)
			throws ResourceNotFoundException {
		
		Room room = roomRepository
				.findById(roomId)
				.orElseThrow(() -> new ResourceNotFoundException("Room not found for this id: " + roomId))
		;
		
		roomRepository.delete(room);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;
	}
	
}
