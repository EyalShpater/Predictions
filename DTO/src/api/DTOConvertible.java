package api;

public interface DTOConvertible<F, T> {
    T convertToDTO();
    F revertFromDTO(T dto);
}
