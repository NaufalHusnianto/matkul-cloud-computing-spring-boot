package cc.k3522055.barang.controller;

import cc.k3522055.barang.dto.OutputDto;
import cc.k3522055.barang.dto.StudentDto;
import cc.k3522055.barang.dto.StudentRegisterDto;
import cc.k3522055.barang.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    public StudentService studentService;

    @PostMapping("/registration")
    public ResponseEntity<OutputDto<StudentDto>> registration(@RequestBody StudentRegisterDto studentRegisterDto) {
        StudentDto studentDto = studentService.registerNewStudent(studentRegisterDto);
        OutputDto<StudentDto> outputDto = new OutputDto<>();
        outputDto.setData(studentDto);
        outputDto.setMessage("Registration successful");
        return ResponseEntity.ok(outputDto);
    }

    @GetMapping("/show-all")
    public ResponseEntity<OutputDto<List<StudentDto>>> showAllStudents() {
        List<StudentDto> studentDtoList = studentService.getAllStudents();
        OutputDto<List<StudentDto>> output = new OutputDto<>();
        output.setData(studentDtoList);
        output.setMessage("All students listed");
        return ResponseEntity.ok(output);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<OutputDto<StudentDto>> findStudentById(@PathVariable("id") Long id) {
        StudentDto studentDto = studentService.getStudent(id);
        OutputDto<StudentDto> outputDto = new OutputDto<>();
        outputDto.setData(studentDto);
        if(studentDto == null) {
            outputDto.setMessage("Student not found");
        } else {
            outputDto.setMessage("Student found");
        }
        return ResponseEntity.ok(outputDto);
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<OutputDto<StudentDto>> destroyStudent(@PathVariable("id") Long id) {
        StudentDto studentDto = new StudentDto();
        OutputDto<StudentDto> outputDto = new OutputDto<>();
        try {
            studentService.deleteStudent(id);
            studentDto = studentService.getStudent(id);
            outputDto.setData(studentDto);
            outputDto.setMessage("Student deleted");
            return ResponseEntity.ok(outputDto);
        } catch (Exception e) {
            outputDto.setMessage(e.getMessage());
            outputDto.setData(studentDto);
            return ResponseEntity.ok(outputDto);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<OutputDto<StudentDto>> updateStudent(@PathVariable("id") Long id, @RequestBody StudentDto studentDto) {
        StudentDto result = studentService.updateStudent(id, studentDto);
        OutputDto<StudentDto> output = new OutputDto<>();
        output.setData(result);
        output.setMessage("Student updated");
        return ResponseEntity.ok(output);
    }

    @GetMapping("/find-name/{name}")
    public ResponseEntity<OutputDto<List<StudentDto>>> showStudentByName(@PathVariable("name") String name) {
        List<StudentDto> studentDtoList = studentService.getStudentByName(name);
        OutputDto<List<StudentDto>> output = new OutputDto<>();
        output.setData(studentDtoList);
        output.setMessage("All students listed");
        return ResponseEntity.ok(output);
    }
}
