package in.regalauction.infrastructure.persistence.hibernate;

import in.regalauction.domain.model.attachment.Attachment;
import in.regalauction.domain.model.attachment.AttachmentRepository;

public class AttachmentRepositoryHibernate extends HibernateRepository
		implements AttachmentRepository {

	@Override
	public Attachment findByCode(final String code) {
		return (Attachment) getSession().createQuery("from Attachment where code = :code")
				.setParameter("code", code)
				.uniqueResult();
	}

	@Override
	public void store(final Attachment attachment) {
		getSession().saveOrUpdate(attachment);
	}

}
